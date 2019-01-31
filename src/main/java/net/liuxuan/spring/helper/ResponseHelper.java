package net.liuxuan.spring.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.ResponseHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/1/18 8:05
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/1/18  |    Moses       |     Created
 */
public class ResponseHelper {

    public static void fastErrorResponseAsJson(HttpServletResponse response, String error,String status,String msg) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> rtnData = new HashMap<String, Object>();
        rtnData.put("error", error);
        rtnData.put("status", status);
        rtnData.put("msg", msg);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeValue(response.getWriter(), rtnData);
    }


    //写一个map到Json Response中
    public static void writeMapToResponseAsJson(HttpServletResponse response, Map<String, Object> rtnData) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeValue(response.getWriter(), rtnData);
    }

    public static void writeObjectToResponseAsJson(HttpServletResponse response, Object rtnData) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeValue(response.getWriter(), rtnData);
    }
}
