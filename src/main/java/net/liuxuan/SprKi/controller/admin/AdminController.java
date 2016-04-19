package net.liuxuan.SprKi.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.admin.AdminController
 * 功能: 后台管理
 * 版本:	@version 1.0
 * 编制日期: 2016/3/23 9:54
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/23  |    Moses       |     Created
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static Logger log = LoggerFactory.getLogger(AdminController.class);


    @RequestMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String home(Map<String, Object> model) {
        log.trace("-Access AdminController.home() Method");
//
//        model.put("message", "Hello World");
//        model.put("title", "Hello Home");
//        model.put("date", new Date());
        return "admin/index";
    }

//    @RequestMapping("userManage")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String userManage(Map<String, Object> model) {
////
//        return "admin/userManage :: middle";
//    }

    @RequestMapping("{pageName}_admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(@PathVariable("pageName") String pageName, Map<String, Object> model) {
////
//        UserDetailInfo userDetailInfo = new UserDetailInfo();
//        List<Users> users = userDetailInfoService.listAllUsers();
//        model.put("user", userDetailInfo);
//        model.put("users", users);
        return "admin/" + pageName + " :: middle";
//        return "admin/profile :: middle";
    }


}
