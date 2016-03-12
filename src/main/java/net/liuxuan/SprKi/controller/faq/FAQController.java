package net.liuxuan.SprKi.controller.faq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.faq.FAQController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/8 15:52
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/8  |    Moses       |     Created
 */

@Controller
public class FAQController {
    private static Logger log =  LoggerFactory.getLogger(FAQController.class);
    @RequestMapping(value = "/faq", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQ(Map<String, Object> model) {
        log.info("-FAQController.getFAQ() Method");
//        model.put("message", "Editor");
        model.put("title", "FAQ 编辑界面");
        return "faq/faq_edit";
    }


    @RequestMapping(value = "/faqpost", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String postFAQ(HttpServletRequest request, Map<String, Object> model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("===Parameter key:{} ,values{}:{}",s,i,strings[i]);
            }
        }

        model.put("title", "FAQ 编辑界面");
        return "faq/faq_edit";

    }
}
