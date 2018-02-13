package net.liuxuan.supportsystem.controller.supporttools;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.supporttools.UnitConverterController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/10/26 8:46
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/10/26  |    Moses       |     Created
 */

@Controller
@RequestMapping("/tools")
public class UnitConverterController {

    @RequestMapping("/unitconv")
    public String getConverter(HttpServletRequest request,
                               HttpServletResponse response, Map<String, Object> model){

//        model.put("title", "MethodNotSupported");
//        model.put("message", "MethodNotSupported");
//        model.put("date", new Date());
//        model.put("status", "405");
        return "tools/unitconv";
    }

    @RequestMapping("/unitconv2")
    public String getConverter2(HttpServletRequest request,
                               HttpServletResponse response, Map<String, Object> model){

//        model.put("title", "MethodNotSupported");
//        model.put("message", "MethodNotSupported");
//        model.put("date", new Date());
//        model.put("status", "405");
        return "tools/unitconvert";
    }
}
