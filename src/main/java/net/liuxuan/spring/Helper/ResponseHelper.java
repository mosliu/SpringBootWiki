package net.liuxuan.spring.Helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public static void writeMAPtoResponseAsJson(HttpServletResponse response, Map<String, Object> rtnData) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.writeValue(response.getWriter(), rtnData);
    }
}
