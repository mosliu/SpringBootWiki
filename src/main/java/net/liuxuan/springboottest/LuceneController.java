package net.liuxuan.springboottest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lucene")
public class LuceneController {

    private static final String STARTTAG = "<font color='red'>";
    private static final String ENDTAG = "</font>";
    private static Logger log = LoggerFactory.getLogger(LuceneController.class);
    @Autowired(required = false)
    IndexWriter indexWriter;
    @Autowired(required = false)
    Analyzer analyzer;

    //    @ResponseBody
    @RequestMapping
    public String index() {
//        return new ModelAndView("lucene");
        return "lucene";
    }


    @ResponseBody
    @RequestMapping("listIndexed")
    public void listIndexed(HttpServletResponse response) throws CorruptIndexException, IOException {
        IndexSearcher indexSearcher = getSearcher();
        int size = indexWriter.maxDoc();
        Document doc = null;
        News news = null;
        List<News> list = new ArrayList<News>();
        for (int i = 0; i < size; i++) {
            news = new News();
            doc = indexSearcher.doc(i);
            news.setTitle(doc.get("title"));
            news.setUrl(doc.get("url"));
            list.add(news);
        }
//        Gson g = new Gson();
//        response.getWriter().write(g.toJson(list));
//        return g.toJson(list);


//        FAQContent faqContent = null;
//        List<FAQContent> list = new ArrayList<FAQContent>();
//        for (int i = 0; i < size; i++) {
//            faqContent = new FAQContent();
//            doc = indexSearcher.doc(i);
//            faqContent.setAnswer(doc.get("answer"));
//            faqContent.setQuestion(doc.get("question"));
//            faqContent.setTitle(doc.get("title"));
////            faqContent.setUrl(doc.get("url"));
//            list.add(faqContent);
//        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeValue(response.getWriter(), list);

//        return JSONObject.toJSONString(list);
    }

    //    @ResponseBody
    @RequestMapping("indexFiles")
    public void indexFiles(HttpServletResponse response) throws IOException {
        Directory d = indexWriter.getDirectory();
        String[] fs = d.listAll();
        Gson g = new Gson();
        response.getWriter().write(g.toJson(fs));
//        return g.toJson(fs);
//        return JSONObject.toJSONString(fs);
    }

    @ResponseBody
    @RequestMapping("deleteIndexes")
    public String deleteIndexes() {

        String flag = "";

        try {
            indexWriter.deleteAll();
            indexWriter.commit();
            flag = "suc";
        } catch (IOException e) {
            log.error("IOException!",e);
//            e.printStackTrace();
            try {
                indexWriter.rollback();
            } catch (IOException e1) {
                log.error("RollBack Error!",e1);
//                e1.printStackTrace();
            }
            flag = "error";
        }

        return flag;
    }

    @ResponseBody
    @RequestMapping("deleteIndex")
    public String deleteIndex() {


        return null;
    }

    @ResponseBody
    @RequestMapping("updateIndex")
    public String updateIndex() {

        return null;
    }

    @ResponseBody
    @RequestMapping("search")
    public String search(String text) throws ParseException, IOException, InvalidTokenOffsetsException {
        log.info("search:{}", text);

        IndexSearcher searcher = getSearcher();

//        QueryParser parser = new QueryParser("title",analyzer);

        QueryParser parser = new MultiFieldQueryParser(new String[]{"title", "content"}, analyzer);
        log.info("parser null? :{}", parser == null);
        log.info("analyzer null? :{}", analyzer == null);


        Query query = parser.parse(text);

        TopDocs td = searcher.search(query, 10);
        System.out.println("总共匹配多少个：" + td.totalHits);
        ScoreDoc[] sd = td.scoreDocs;

        SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter(STARTTAG, ENDTAG);

        Highlighter highlighter = new Highlighter(simpleHtmlFormatter, new QueryScorer(query));

        Document doc;

        TokenStream tokenStream = null;

        News news = null;
        List<News> list = new ArrayList<News>();

        String title;
        String content;

        for (int i = 0; i < sd.length; i++) {
            news = new News();

            int docId = sd[i].doc;
            doc = searcher.doc(docId);

            title = doc.get("title");
            tokenStream = analyzer.tokenStream("title", new StringReader(title));
            title = highlighter.getBestFragment(tokenStream, title);
            news.setTitle(title == null ? doc.get("title") : title);

            content = doc.get("content");
            tokenStream = analyzer.tokenStream("content", new StringReader(content));
            content = highlighter.getBestFragment(tokenStream, content);

            //正文部分，如果没有匹配的关键字，截取前200个字符
            news.setContent(content == null ? (doc.get("content").length() < 200 ? doc.get("content") : doc.get("content").substring(0, 199)) : content);
            news.setUrl(doc.get("url"));
            news.setDate(doc.get("date"));
            news.setOther1(docId + "");
            list.add(news);

        }
        Gson g = new Gson();
        return g.toJson(list);
//        return JSONObject.toJSONString(list);
    }

    @SuppressWarnings("deprecation")
    private IndexSearcher getSearcher() throws IOException {

//        Directory dir = FSDirectory.open(Paths.get("./index"));
        IndexReader reader = DirectoryReader.open(indexWriter.getDirectory());
        IndexSearcher is = new IndexSearcher(reader);
        return is;
//        return new IndexSearcher(IndexReader.open());
    }

}
