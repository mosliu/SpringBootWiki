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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Moses
 */


@RestController
public class Test01 {

    private static Log logger = LogFactory.getLog(Test01.class);
    //    @Value("name")
    @Value("${name:CCCC}")
    private String name;

    @Autowired
    DBSettings db;



    @RequestMapping("/asd")
    public String home() {

//        System.out.println(conf.getServers().size());
        System.out.println("dburl"+db.getUrl());
//        Log.d(TAG, "home() called with " + name);

        return String.format("Hello %s!", name);
    }



}