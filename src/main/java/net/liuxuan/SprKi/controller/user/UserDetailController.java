package net.liuxuan.SprKi.controller.user;

import net.liuxuan.SprKi.entity.Message;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.service.user.UserDetailInfoService;
import net.liuxuan.spring.Helper.SystemHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.user.UserDetailController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/3 10:02
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/3  |    Moses       |     Created
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class UserDetailController {
    private static Logger log =  LoggerFactory.getLogger(UserDetailController.class);

    @Autowired
    UserDetailInfoService userDetailInfoService;


    @RequestMapping(value = "/user/detail", method = RequestMethod.GET)
    public String getCurrentUser(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        log.info("-UserDetailController.getCurrentUser() Method");
        UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();
//        model.put("message", "Editor");

        model.put("user", currentUserDetailInfo);
        return "user/user_edit";
    }
}
