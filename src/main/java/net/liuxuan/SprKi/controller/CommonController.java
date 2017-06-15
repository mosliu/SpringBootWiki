package net.liuxuan.SprKi.controller;

import net.liuxuan.SprKi.controller.utils.FileUploadUtil;
import net.liuxuan.SprKi.service.labthink.DepartmentService;
import net.liuxuan.SprKi.service.user.UserDetailInfoService;
import net.liuxuan.spring.Helper.SpringContextHelper;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.CommonController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/12 8:26
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/12  |    Moses       |     Created
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CommonController {


    /**
     * The Request mapping handler mapping.
     */
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;


    private Set<String> UrlMappingList;

    /**
     * Init.
     */
    @PostConstruct
    public void init(){
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = handlerMethods.keySet();
        UrlMappingList = requestMappingInfos
                .stream()
                .map(e -> e.getPatternsCondition().getPatterns())
                .collect(Collectors.toList()).stream()
                .flatMap(e -> e.stream())
                .collect(Collectors.toSet());
    }

    /**
     * Gets msg.
     *
     * @param path     the path
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the msg
     */
    @RequestMapping(value = "/goget/{path}", method = RequestMethod.GET)
    public String getMsg(
            @PathVariable String path,
            HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        System.out.println("PATH IS:"+path);
        path = path.replaceAll("-","/");
        System.out.println(UrlMappingList.contains("/"+path));
        if(UrlMappingList.contains("/"+path)){
            return "redirect:/"+path;
        }

        System.out.println("PATH TRANSLATE TO:"+path);

        return path;
    }

    /**
     * 为所有的用户加上头像
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return string
     */
    @RequestMapping(value = "/func/add_avatar", method = RequestMethod.GET)
    @ResponseBody
    public String addAvatar(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        UserDetailInfoService userDetailInfoService = (UserDetailInfoService) SpringContextHelper.getBean("userDetailInfoServiceImpl");
        userDetailInfoService.checkAllUserDetailInfoAvatar();
        return "OK";
    }

    /**
     * Add department role string.
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the string
     */
    @RequestMapping(value = "/func/addDepartmentRole", method = RequestMethod.GET)
    @ResponseBody
    public String addDepartmentRole(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        DepartmentService departmentServiceImpl = (DepartmentService) SpringContextHelper.getBean("departmentServiceImpl");
        departmentServiceImpl.checkDeparmentRole();
        return "OK";
    }

}
