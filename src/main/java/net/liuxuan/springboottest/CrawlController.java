package net.liuxuan.springboottest;

import java.util.List;

import com.google.gson.Gson;
import org.apache.commons.collections.functors.ForClosure;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/crawl")
public class CrawlController {

	private static Logger log =  LoggerFactory.getLogger(CrawlController.class);

	@Autowired(required = false) IndexWriter indexWriter;
	@Autowired(required = false) IndexSearcher indexSearcher;

	@ResponseBody
	@RequestMapping("ntes")
	public String ntes(String category) throws Exception{
		//抓取网易新闻头条
		log.info("category:{}",category);


		List<News> list=NTES.crawl163LatestNews(category);
//		System.out.println("zhuaqu");
//		System.out.println(list.size());
//		for (News news : list) {
//			System.out.println(news.getUrl());
//		}
		//重复抓取会重复添加索引
		//indexWriter.deleteAll();
		
		Document doc = null;

		for (News news : list) {
			doc = new Document();
			
			Field title = new Field("title", news.getTitle(), Field.Store.YES,Field.Index.ANALYZED);
//			Field title = new StringField("title", news.getTitle(), Field.Store.YES);
			Field content = new Field("content", news.getContent(),Field.Store.YES, Field.Index.ANALYZED);
//			Field content = new StringField("content", news.getContent(),Field.Store.YES);
			Field shortContent = new Field("shortContent", news.getShortContent(),Field.Store.YES, Field.Index.NO);
//			Field shortContent = new StringField("shortContent", news.getShortContent(),Field.Store.YES);
//			shortContent.fieldType().setIndexOptions(IndexOptions.NONE);
//			Field url = new Field("url", news.getUrl(), Field.Store.YES,Field.Index.NO);
			Field url = new Field("url", news.getUrl(), Field.Store.YES,Field.Index.NO);
//			Field date = new Field("date", news.getDate(), Field.Store.YES,Field.Index.NO);
			Field date = new Field("date", news.getDate(), Field.Store.YES,Field.Index.NO);
			doc.add(title);
			doc.add(content);
			doc.add(shortContent);
			doc.add(url);
			doc.add(date);
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		Gson g =new Gson();

		return g.toJson(list);
	}
	
}
