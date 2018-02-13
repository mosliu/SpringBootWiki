package net.liuxuan.supportsystem.controller.admin;

import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.spring.Helper.ResponseHelper;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private static Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String home(HttpServletRequest request, Map<String, Object> model) {
        log.trace("-Access AdminController.home() Method");

        SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN,"","ACCESSADMIN","");
//
//        model.put("message", "Hello World");
//        model.put("title", "Hello Home");
//        model.put("date", new Date());
        return "admin/index";
    }

//    @RequestMapping("userManage")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String userManage(Map<String, Object> model) {
//
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

    @RequestMapping("userisadmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void getPages(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("admin",true);
        map.put("authtype",request.getAuthType());
        ResponseHelper.writeMapToResponseAsJson(response,map);
////
    }
}
