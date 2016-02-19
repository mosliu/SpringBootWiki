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
}
