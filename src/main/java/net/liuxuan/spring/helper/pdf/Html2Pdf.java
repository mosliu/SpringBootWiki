package net.liuxuan.spring.helper.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import net.liuxuan.spring.helper.HttpClientHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Random;


public class Html2Pdf {

    public static String createPdf(HttpServletRequest request) throws Exception {
        try {
            String destDir = getServicePath(request) + "resources/upload/pdf";
            // 根据当前时间获取文件夹名（0/1）
            long newFile = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)) % 2;

            // 创建文件夹
            createFile(destDir + "/" + newFile);
            long oldFile = (newFile + 1) % 2;
            // 删除昨天的文件夹
            deleteFile(destDir + "/" + oldFile);

            destDir += "/" + newFile + "/interface" + getChar(32) + ".pdf";

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destDir));
            document.open();

            InputStream pdfText = null;
            try {
//				pdfText = HttpClientHelper.getInputStream(ConfigHelper.domain+ "/front/interface/detail/pdf.do?id=" + interFaceId + "&moduleId="+moduleId+"&secretKey="+secretKey);
                pdfText = HttpClientHelper.getInputStream("http://127.0.0.1/tools/unitconv");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pdfText == null) {
//				pdfText = HttpClientHelper.getInputStream("http://api.crap.cn/result.do?result="+URLEncoder.encode("地址有误，生成pdf失败，请确认配置文件config.properties中的网站域名配置是否正确！","utf-8"));
            }

            XMLWorkerHelper.getInstance().parseXHtml(writer, document, pdfText,
                    Charset.forName("UTF-8"), new ChinaFont());
            document.close();
            return destDir;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static String getServicePath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/") + "/";
    }

    // 创建文件夹
    public static void createFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 通过递归调用删除一个文件夹及下面的所有文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {//表示该文件不是文件夹
            file.delete();
        } else {
            //首先得到当前的路径
            String[] childFilePaths = file.list();
            for (String childFilePath : childFilePaths) {
                deleteFile(file.getAbsolutePath() + "/" + childFilePath);
            }
            file.delete();
        }
    }

    public static String getChar(int num) {
        String md = "123456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ789abcd";
        Random random = new Random();
        String temp = "";
        for (int i = 0; i < num; i++) {
            temp = temp + md.charAt(random.nextInt(50));
        }
        return temp;
    }


}
