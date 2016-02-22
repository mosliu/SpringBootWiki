/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.liuxuan.springboottest;


import net.liuxuan.wiki.db.DBSettings;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Moses
 */


@RestController
@RequestMapping("/api")
public class Test01 {

    private static Log logger = LogFactory.getLog(Test01.class);
    @Autowired
    DBSettings db;
    //    @Value("name")
    @Value("${name:CCCC}")
    private String name;

    @RequestMapping("/asd")
    public String home() {

//        System.out.println(conf.getServers().size());
        System.out.println("dburl" + db.getUrl());
//        Log.d(TAG, "home() called with " + name);

        return String.format("Hello %s!", name);
    }

    @RequestMapping(value = "/do", produces = "application/json")
    public String AUTH_API() {

        return "Hello API";
    }

    @RequestMapping(value = "/jsp")
    public void AUTH_JSP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getOutputStream().print("hello Rest jsp");

//        return String.format("Hello API");
    }

}