package test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  test.HttpClientOCR
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/11/28 9:47
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/28  |    Moses       |     Created
 */
public class HttpClientOCR {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BCE_PREFIX = "x-bce-";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);
    private static final Set<String> defaultHeadersToSign = Sets.newHashSet();
    private static final Joiner queryStringJoiner = Joiner.on('&');
    private static BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
    private static String[] PERCENT_ENCODED_STRINGS = new String[256];
    static {
        /*
         * StringBuilder pattern = new StringBuilder();
         *
         * pattern .append(Pattern.quote("+")) .append("|") .append(Pattern.quote("*")) .append("|")
         * .append(Pattern.quote("%7E")) .append("|") .append(Pattern.quote("%2F"));
         *
         * ENCODED_CHARACTERS_PATTERN = Pattern.compile(pattern.toString());
         */
        for (int i = 'a'; i <= 'z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        for (int i = '0'; i <= '9'; i++) {
            URI_UNRESERVED_CHARACTERS.set(i);
        }
        URI_UNRESERVED_CHARACTERS.set('-');
        URI_UNRESERVED_CHARACTERS.set('.');
        URI_UNRESERVED_CHARACTERS.set('_');
        URI_UNRESERVED_CHARACTERS.set('~');

        for (int i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
            PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
        }
    }
    public static void main(String[] args) {
//        doAPost();
        doAJsonPost();
    }

    private static void doAJsonPost() {
        String url = "http://ocr.bj.baidubce.com/v1/recognize/text";
        JsonObject json = new JsonObject();

        String ACCESS_KEY_ID = "b7d11214c8fc452db3de12028cf46daa";                   // 用户的Access Key ID
        String SECRET_ACCESS_KEY = "64631fe987f4423bb0a117101bf90a45";           // 用户的Secret Access Key

        String accessKeyId = ACCESS_KEY_ID;
        String secretAccessKey = SECRET_ACCESS_KEY;

        String path = "/v1/recognize/text";

        //获取UTC 时间
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String utcTime = format.format(now);
        //获取图片
        String image = encodeImgageToBase64(new File("d:/aaaaa.jpg"));
//        String image = encodeImgageToBase64(new File("d:/che4.jpg"));
//        String image = "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";
        json.addProperty("base64", image);
        json.addProperty("language", "CHE_ENG");


        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        JsonObject response = null;
        try {
            StringEntity stringEntity = new StringEntity(json.toString());
            stringEntity.setContentEncoding(DEFAULT_ENCODING);
            stringEntity.setContentType("application/json");

            httpPost.setEntity(stringEntity);

            httpPost.setConfig(RequestConfig.DEFAULT);
//            System.out.println(httpPost.getFirstHeader("Content-Length").getValue());
            System.out.println(String.valueOf(stringEntity.getContentLength()));
//            httpPost.addHeader("Content-Length", String.valueOf(stringEntity.getContentLength()));
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("host", "ocr.bj.baidubce.com");
            httpPost.addHeader("x-bce-date", utcTime);

            String authString = "bce-auth-v1" + "/" + accessKeyId + "/" + utcTime + "/3600";
            String signingKey = sha256Hex(secretAccessKey, authString);


            Map<String, String> parameters = Maps.newHashMap();
            String canonicalURI = path;
            String canonicalQueryString = getCanonicalQueryString(parameters, true);


            SortedMap<String, String> headersToSign = Maps.newTreeMap();
            headersToSign.put("host","ocr.bj.baidubce.com");
            headersToSign.put("content-length",String.valueOf(stringEntity.getContentLength()));
            headersToSign.put("content-type","application/json");
            headersToSign.put("x-bce-date",utcTime);

            String canonicalHeader = getCanonicalHeaders(headersToSign);

            String signedHeaders = "host;content-length;content-type;x-bce-date";


            String canonicalRequest ="POST\n" + canonicalURI + "\n" + canonicalQueryString + "\n" + canonicalHeader;
            // Signing the canonical request using key with sha-256 algorithm.
            String signature = sha256Hex(signingKey, canonicalRequest);
            String authorizationHeader = authString + "/" + signedHeaders + "/" + signature;
            httpPost.addHeader("Authorization", authorizationHeader);
//            httpPost.
            HttpResponse res = closeableHttpClient.execute(httpPost);
            System.out.println("StatusLine:"+res.getStatusLine());
//            if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                HttpEntity entity = res.getEntity();
//                System.out.println(entity.getContent());

//                response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(),charset)));
//            } else {
                if(entity!=null){
//                    String charset = EntityUtils.getContentCharSet(entity);
                    String charset = ContentType.getOrDefault(entity).getCharset().name();
                    response = (JsonObject) new JsonParser().parse(new InputStreamReader(entity.getContent(),charset));
                    System.out.println(response);
                }
                System.out.println(res);
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void doAPost() {
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//        HttpGet httpGet = new HttpGet("http://www.gxnu.edu.cn/default.html");

        HttpPost httpPost = new HttpPost("http://tutor.sturgeon.mopaas.com/tutor/search");
        httpPost.setConfig(RequestConfig.DEFAULT);
        System.out.println(httpPost.getRequestLine());
        System.out.println("=================");
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("searchText", "英语"));

        UrlEncodedFormEntity entity;
        try {
            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);

            HttpResponse httpResponse;
            //post请求
            httpResponse = closeableHttpClient.execute(httpPost);
            //获取响应消息实体
            //getEntity()
            HttpEntity httpEntity = httpResponse.getEntity();

            //响应状态
            System.out.println("status:" + httpResponse.getStatusLine());
            //判断响应实体是否为空
            if (httpEntity != null) {
                System.out.println("contentEncoding:" + entity.getContentEncoding());
                System.out.println("response content:" + EntityUtils.toString(httpEntity, "UTF-8"));
            }
//
//            if (httpEntity != null) {
//                //打印响应内容
//                System.out.println("response:" + EntityUtils.toString(httpEntity, "UTF-8"));
//            }
            //释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将本地图片进行Base64位编码
     *
     * @param imageFile 图片的url路径，如d:\\中文.jpg
     * @return
     */
    public static String encodeImgageToBase64(File imageFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
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

    private static String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
            return new String(Hex.encodeHex(mac.doFinal(stringToSign.getBytes(UTF8))));
        } catch (Exception e) {
            throw new OCRException("Fail to generate the signature", e);
        }
    }

    public static String getCanonicalQueryString(Map<String, String> parameters, boolean forSignature) {
        if (parameters.isEmpty()) {
            return "";
        }

        List<String> parameterStrings = Lists.newArrayList();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (forSignature && "Authorization".equalsIgnoreCase(entry.getKey())) {
                continue;
            }
            String key = entry.getKey();
            checkNotNull(key, "parameter key should not be null");
            String value = entry.getValue();
            if (value == null) {
                if (forSignature) {
                    parameterStrings.add(normalize(key) + '=');
                } else {
                    parameterStrings.add(normalize(key));
                }
            } else {
                parameterStrings.add(normalize(key) + '=' + normalize(value));
            }
        }
        Collections.sort(parameterStrings);

        return queryStringJoiner.join(parameterStrings);
    }

    /**
     * Normalize a string for use in BCE web service APIs. The normalization algorithm is:
     * <p>
     * <ol>
     * <li>Convert the string into a UTF-8 byte array.</li>
     * <li>Encode all octets into percent-encoding, except all URI unreserved characters per the RFC 3986.</li>
     * </ol>
     * <p>
     * <p>
     * All letters used in the percent-encoding are in uppercase.
     *
     * @param value the string to normalize.
     * @return the normalized string.
     * @throws UnsupportedEncodingException
     */
    public static String normalize(String value) {
        try {
            StringBuilder builder = new StringBuilder();
            for (byte b : value.getBytes(DEFAULT_ENCODING)) {
                if (URI_UNRESERVED_CHARACTERS.get(b & 0xFF)) {
                    builder.append((char) b);
                } else {
                    builder.append(PERCENT_ENCODED_STRINGS[b & 0xFF]);
                }
            }
            return builder.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Joiner headerJoiner = Joiner.on('\n');
    private static String getCanonicalHeaders(SortedMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        }

        List<String> headerStrings = Lists.newArrayList();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            headerStrings.add(normalize(key.trim().toLowerCase()) + ':' + normalize(value.trim()));
        }
        Collections.sort(headerStrings);

        return headerJoiner.join(headerStrings);
    }

    private boolean isDefaultHeaderToSign(String header) {
        header = header.trim().toLowerCase();
        return header.startsWith(BCE_PREFIX) || defaultHeadersToSign.contains(header);
    }

    public static class OCRException extends RuntimeException {
        private static final long serialVersionUID = -9085416005820812953L;

        /**
         * Constructs a new BceClientException with the specified detail message.
         *
         * @param message the detail error message.
         */
        public OCRException(String message) {
            super(message);
        }

        /**
         * Constructs a new BceClientException with the specified detail message and the underlying cause.
         *
         * @param message the detail error message.
         * @param cause   the underlying cause of this exception.
         */
        public OCRException(String message, Throwable cause) {
            super(message, cause);
        }

    }
}