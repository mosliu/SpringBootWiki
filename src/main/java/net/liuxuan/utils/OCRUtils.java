package net.liuxuan.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * 源文件名:  net.liuxuan.utils.OCRUtils
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/11/30 16:32
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/30  |    Moses       |     Created
 */
public class OCRUtils {
    public static final String AUTHORIZATION = "Authorization";
    public static final String BCE_PREFIX = "x-bce-";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);
    private static final Set<String> defaultHeadersToSign = Sets.newHashSet();
    private static final Joiner queryStringJoiner = Joiner.on('&');
    private static final Joiner headerJoiner = Joiner.on('\n');
    private static BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
    private static String[] PERCENT_ENCODED_STRINGS = new String[256];
//    private static String ACCESS_KEY_ID = "b7d11214c8fc452db3de12028cf46daa";                   // 用户的Access Key ID
//    private static String SECRET_ACCESS_KEY = "64631fe987f4423bb0a117101bf90a45";           // 用户的Secret Access Key
    private static String ACCESS_KEY_ID = "2cdc425267b8483c867bb6e716401f53";                   // 用户的Access Key ID
    private static String SECRET_ACCESS_KEY = "aaa331aaae8648bd9d352a70d4ca5f96";           // 用户的Secret Access Key
    private static String OCRUrl = "http://ocr.bj.baidubce.com/v1/recognize/text";
    private static String OCRPath = "/v1/recognize/text";

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

    private static Logger log =  LoggerFactory.getLogger(OCRUtils.class);

    public static String getOcrByBase64(String base64){

        JsonObject json = new JsonObject();
        //获取UTC 时间
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String utcTime = format.format(now);
        //获取图片

        json.addProperty("base64", base64);
        json.addProperty("language", "CHE_ENG");

        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(OCRUrl);
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

            String authString = "bce-auth-v1" + "/" + ACCESS_KEY_ID + "/" + utcTime + "/3600";
            String signingKey = sha256Hex(SECRET_ACCESS_KEY, authString);


            Map<String, String> parameters = Maps.newHashMap();
            String canonicalURI = OCRPath;
            String canonicalQueryString = getCanonicalQueryString(parameters, true);


            SortedMap<String, String> headersToSign = Maps.newTreeMap();
            headersToSign.put("host", "ocr.bj.baidubce.com");
            headersToSign.put("content-length", String.valueOf(stringEntity.getContentLength()));
            headersToSign.put("content-type", "application/json");
            headersToSign.put("x-bce-date", utcTime);

            String canonicalHeader = getCanonicalHeaders(headersToSign);

            String signedHeaders = "host;content-length;content-type;x-bce-date";


            String canonicalRequest = "POST\n" + canonicalURI + "\n" + canonicalQueryString + "\n" + canonicalHeader;
            // Signing the canonical request using key with sha-256 algorithm.
            String signature = sha256Hex(signingKey, canonicalRequest);
            String authorizationHeader = authString + "/" + signedHeaders + "/" + signature;
            httpPost.addHeader("Authorization", authorizationHeader);
//            httpPost.
            HttpResponse res = closeableHttpClient.execute(httpPost);
            System.out.println("StatusLine:" + res.getStatusLine());
//            if (res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
            HttpEntity entity = res.getEntity();
//                System.out.println(entity.getContent());

//                response = new JSONObject(new JSONTokener(new InputStreamReader(entity.getContent(),charset)));
//            } else {
            if (entity != null) {
//                    String charset = EntityUtils.getContentCharSet(entity);
                String charset = ContentType.getOrDefault(entity).getCharset().name();
                response = (JsonObject) new JsonParser().parse(new InputStreamReader(entity.getContent(), charset));
                System.out.println(response);
            }
            System.out.println(res);
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response.toString();
    }

    public static String getOcrByImageFile(File image){
//        String base64 = encodeImageToBase64(new File("d:/aaaaa.jpg"));
        String base64 = encodeImageToBase64(image);
        return getOcrByBase64(base64);
    }


    /**
     * 将本地图片进行Base64位编码
     *
     * @param imageFile 图片的url路径，如d:\\中文.jpg
     * @return
     */
    public static String encodeImageToBase64(File imageFile) {
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
            log.error("IOException",e);
//            e.printStackTrace();
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

    private static String getCanonicalQueryString(Map<String, String> parameters, boolean forSignature) {
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
    private static String normalize(String value) {
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
