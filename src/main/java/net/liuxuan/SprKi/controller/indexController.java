/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by Moses on 2016/2/14.
 */

@Controller
public class IndexController {

    private static Logger log =  LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public String home(Map<String, Object> model) {
        log.debug("Access IndexController.home() Method");

        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @RequestMapping(value = "/editor", method = RequestMethod.GET)
    //@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public String editor(Map<String, Object> model) {
        log.debug("Access IndexController.editor() Method");

        model.put("message", "Editor");
        model.put("title", "Hello Editor");
        model.put("date", new Date());
        return "editor";

    }

    @RequestMapping(value = "/editor", method = RequestMethod.POST)
    public String editorsave(HttpServletRequest request, Map<String, Object> model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("Parameter key:{} ,values{}:{}",s,i,strings[i]);
            }
        }

        log.debug("Access IndexController.editor() Post Method");

        model.put("message", "Editor");
        model.put("title", "Hello Editor");
        model.put("date", new Date());
        return "editor";

    }

}
