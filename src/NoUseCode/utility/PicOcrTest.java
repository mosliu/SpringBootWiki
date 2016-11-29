package test;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceV1Signer;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.auth.SignOptions;
import com.baidubce.http.BceHttpClient;
import com.baidubce.http.BceHttpResponse;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceMetadataResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.model.AbstractBceResponse;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.HttpClient;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//import com.oa.commons.util.BASE64;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.PicOcrTest
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/11/24 11:02
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/24  |    Moses       |     Created
 */
public class PicOcrTest {
//    public static String request(String httpUrl, String httpArg) {
//        BufferedReader reader = null;
//        String result = null;
//        StringBuffer sbf = new StringBuffer();
//
//        try {
//            URL url = new URL(httpUrl);
//            HttpURLConnection connection = (HttpURLConnection) url
//                    .openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//            // 填入apikey到HTTP header
//            connection.setRequestProperty("apikey", "您自己的apikey");
//            connection.setDoOutput(true);
//            connection.getOutputStream().write(httpArg.getBytes("UTF-8"));
//            connection.connect();
//            InputStream is = connection.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            String strRead = null;
//            while ((strRead = reader.readLine()) != null) {
//                sbf.append(strRead);
//                sbf.append("\r\n");
//            }
//            reader.close();
//            result = sbf.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }

    /**
     * @param args
     */
    public static void main(String[] args) {
//        File file = new File("d:\\che4.jpg");
////        String imageBase = BASE64.encodeImgageToBase64(file);
//        imageBase = imageBase.replaceAll("\r\n","");
//        imageBase = imageBase.replaceAll("\\+","%2B");
//        String httpUrl = "http://apis.baidu.com/apistore/idlocr/ocr";
//        String httpArg = "fromdevice=pc&clientip=10.10.10.0&detecttype=LocateRecognize&languagetype=CHN_ENG&imagetype=1&image="+imageBase;
//        String jsonResult = request(httpUrl, httpArg);
//        System.out.println("返回的结果--------->"+jsonResult);
        PicOcrTest tester = new PicOcrTest();
        tester.run();


    }

    /**
     * 将本地图片进行Base64位编码
     *
     * @param imageFile 图片的url路径，如d:\\中文.jpg
     * @return
     */
    public static String encodeImgageToBase64(File imageFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        // 其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
        return Base64.getEncoder().encodeToString(data);// 返回Base64编码过的字节数组字符串
    }

    private void run() {
//        String ACCESS_KEY_ID = "3ce1580435294ef8ad731f06b52181a7";                   // 用户的Access Key ID
//        String SECRET_ACCESS_KEY = "526d5eba5bde457c8a74fde31f69eb33";           // 用户的Secret Access Key
        String ACCESS_KEY_ID = "b7d11214c8fc452db3de12028cf46daa";                   // 用户的Access Key ID
        String SECRET_ACCESS_KEY = "64631fe987f4423bb0a117101bf90a45";           // 用户的Secret Access Key
        try {
            URI uri = new URI("http://ocr.bj.baidubce.com/v1/recognize/text");
//            URI uri = new URI("http://ocr.bj.baidubce.com/api/v1/ocr/general");

            InternalRequest irequest = new InternalRequest(HttpMethodName.POST, uri);
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String time = format.format(now);
            System.out.println(time);
            //Content-Type: text/plain
            irequest.addHeader("content-type", "application/json");
//            irequest.addHeader("content-type", "application/x-www-form-urlencoded");
//            irequest.addHeader("host", "word.bj.baidubce.com");
            irequest.addHeader("x-bce-date", time);
//            irequest.addHeader("accept-encoding", "gzip, deflate");
            //内容体
            JsonObject obj = new JsonObject();
            String image = encodeImgageToBase64(new File("d:/aaaaa.jpg"));
            obj.addProperty("base64", image);

            String contents = obj.toString();

//            irequest.addParameter("base64",image);
//            irequest.addParameter("json",contents);
            byte[] contentbytes = contents.getBytes();
            irequest.setContent(RestartableInputStream.wrap(contentbytes));
            irequest.addHeader("content-length", String.valueOf(contentbytes.length));
            SignOptions options = new SignOptions();

            options.setTimestamp(now);

            BceClientConfiguration config = new BceClientConfiguration();
            config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));

//            Set<String> headersToSign = new HashSet<String>();
            Set<String> headersToSign = Sets.newLinkedHashSet();
//            headersToSign.add("host");
            headersToSign.add("content-length");
            headersToSign.add("content-type");
            headersToSign.add("x-bce-date");
            options.setHeadersToSign(headersToSign);

            irequest.setSignOptions(options);
            BceV1Signer signer = new BceV1Signer();
//            signer.sign(irequest, config.getCredentials(), options);
            BceHttpClient client = new BceHttpClient(config, signer);
            HttpResponseHandler[] responseHandlers = new HttpResponseHandler[]{
                    new BceMetadataResponseHandler(),
//                    new BceErrorResponseHandler(),
                    new myHandler()};
            AbstractBceResponse res = client.execute(irequest, AbstractBceResponse.class, responseHandlers);
//
            System.out.println("========================");
//            System.out.println(irequest);
//            System.out.println("========================");
//            System.out.println(irequest.getUri());
//            System.out.println("========================");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    private class myHandler implements HttpResponseHandler {
        @Override
        public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response) throws Exception {
            System.out.println("StatusText:"+httpResponse.getStatusText());
            InputStream content = httpResponse.getContent();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = content.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            System.out.println(outSteam.toString());
            return true;
        }
    }

}
