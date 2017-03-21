package net.liuxuan.spring.Helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.JsonHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/17 16:11
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/17  |    Moses       |     Created
 */
public class JsonHelper {
    public static String HibernateEntityToJsonString(Object entity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        return mapper.writeValueAsString(entity);
    }

    public static String ObjectToJsonString(Object entity) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new Hibernate5Module());
        return mapper.writeValueAsString(entity);
    }
}
