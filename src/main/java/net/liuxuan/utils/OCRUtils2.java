package net.liuxuan.utils;

import com.baidu.aip.ocr.AipOcr;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.util.Base64;
import java.util.HashMap;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.OCRUtils2
 * 功能: 原功能失效
 * 版本:	@version 1.0
 * 编制日期: 2017/7/7 14:04
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/7/7  |    Moses       |     Created
 */
public class OCRUtils2 {
    public static final String APP_ID = "9856540";
    public static final String API_KEY = "DRxBledKAjhN3psk5Nd4Brj8";
    public static final String SECRET_KEY = "w7Apkm8kPV3ysaZAZTcLl66deOiasKIx";
    public static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    static {
        // 初始化一个OcrClient


        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }
    public static void main(String[] args) throws JSONException {
//        // 初始化一个OcrClient
//
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);


        // 调用通用识别接口
        String genFilePath = "d:/aaaaa.jpg";
        JSONObject genRes = client.basicGeneral(genFilePath, new HashMap<String, String>());
        System.out.println(genRes.toString(2));

        String genFilePath2 = "d:/bbbbb.jpg";
//        JSONObject genRes2 = client.tableRecognitionAsync(genFilePath2);
        // res为使用tableRecognitionAsync接口的json返回值
//        String reqId = genRes2.getJSONArray("result").getJSONObject(0).getString("request_id");
        // 获取json结果
//        System.out.println(client.getTableRecognitionJsonResult(reqId));
        System.out.println(client.getTableRecognitionJsonResult("9856540_705"));
        // 获取excel结果
//        System.out.println(client.getTableRecognitionExcelResult(reqId));

//        System.out.println(genRes2.toString(2));
//        System.out.println(genRes.toString());

    }

    public static String getOcrByBase64(String base64){
        byte[] decode = Base64.getDecoder().decode(base64);
        JSONObject genRes = client.basicGeneral(decode, new HashMap<String, String>());
//        JsonObject response = (JsonObject) new JsonParser().parse(genRes.toString());
        return genRes.toString();
    }
}
