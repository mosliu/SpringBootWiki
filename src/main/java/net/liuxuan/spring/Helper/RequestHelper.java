package net.liuxuan.spring.Helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.RequestHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/17 16:04
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/17  |    Moses       |     Created
 */
public class RequestHelper {
    private static Logger log = LoggerFactory.getLogger(RequestHelper.class);

    public static void showParameters(Map<String, String[]> parameterMap) {
        Assert.notNull(parameterMap);
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("Parameter key:{} ,values{}:{}", s, i, strings[i]);
            }
        }
    }

}
