package net.liuxuan.supportsystem.controller.user;

import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import net.liuxuan.supportsystem.service.user.UserDetailInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
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
    private static Logger log = LoggerFactory.getLogger(UserDetailController.class);

    @Autowired
    private UserDetailInfoService userDetailInfoService;


    @RequestMapping(value = "/user/detail", method = RequestMethod.GET)
    public String getCurrentUser(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        log.info("-UserDetailController.getCurrentUser() Method");
        UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();
//        model.put("message", "Editor");

        model.put("user", currentUserDetailInfo);
        return "user/user_edit";
    }

    @RequestMapping(value = "/user/detail/show/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        UserDetailInfo detailInfo = userDetailInfoService.findUserDetailInfoById(id);

        model.put("user", detailInfo);
        return "user/user_show";
    }

    @RequestMapping(value = "/user/detail/modify", method = RequestMethod.POST)
    public String modifyCurrentUser(UserDetailInfo userDetailInfo, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws InvocationTargetException, IllegalAccessException {

        log.info("-UserDetailController.getCurrentUser() Method");
        UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();
//        model.put("message", "Editor");

        if (StringUtils.equals(userDetailInfo.getDbUser().getUsername(), currentUserDetailInfo.getDbUser().getUsername())) {
            // if(userDetailInfo.getId()==currentUserDetailInfo.getId()) {
            userDetailInfoService.saveUserDetailInfo(userDetailInfo);
            SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.USER_UPDATE, userDetailInfo, "更新用户" + userDetailInfo.getDbUser().getUsername(), "");
            model.put("user", userDetailInfo);
            model.put("title", "保存成功");

        } else {
            model.put("user", currentUserDetailInfo);
            model.put("title", "保存失败，请检查");
        }

//        model.put("success1", "success!");


        return "user/user_edit";
    }

}
