package net.liuxuan.SprKi.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.repository.labthink.DepartmentRepository;
import net.liuxuan.SprKi.service.user.UserDetailInfoService;
import net.liuxuan.spring.Helper.RequestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
    @Autowired
    UserDetailInfoService userDetailInfoService;
    @Resource
    DepartmentRepository departmentRepository;

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
//
        UserDetailInfo userDetailInfo = new UserDetailInfo();
        List<Users> users = userDetailInfoService.listAllUsers();
        model.put("user", userDetailInfo);
        model.put("users", users);
        return "admin/" + pageName + " :: middle";
//        return "admin/profile :: middle";
    }

    @RequestMapping("user")
//    @ResponseBody
    public String userManage(@ModelAttribute("dto") BaseDTO dto, UserDetailInfo userDetailInfo, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model, RedirectAttributesModelMap redirectAttributesModelMap) {
//
        log.info("===userManage logged ,the value is : {}", dto.toString());
        log.info("===userManage logged ,the value is : {}", userDetailInfo.toString());
//        log.info("===userManage logged ,the value is : {}",userDetailInfo.getUsers().toString());


        Users u = new Users();
        u.setUsername(dto.sid);

        switch (dto.action) {
            case "edit":

                userDetailInfo = userDetailInfoService.findUserDetailInfoByUsers(u);
                if (userDetailInfo == null) {
                    redirectAttributesModelMap.put("error", "无用户，请检查进入入口");
//                    model.put("error","无用户，请检查进入入口");
                }
//                redirectAttributesModelMap.put("user", userDetailInfo);
                model.put("user", userDetailInfo);
                return "admin/snipplets/div_user :: useredit";
//                break;
            case "authedit":
                userDetailInfo = userDetailInfoService.findUserDetailInfoByUsers(u);
                if (userDetailInfo == null) {
                    redirectAttributesModelMap.put("error", "无用户，请检查进入入口");
//                    model.put("error","无用户，请检查进入入口");
                }
                List<String> authslist = userDetailInfoService.listAuths();
                redirectAttributesModelMap.put("user", userDetailInfo);
                redirectAttributesModelMap.put("authslist", authslist);
                model.put("user", userDetailInfo);
                model.put("authslist", authslist);
                Set<Authorities> authoritiesSet = userDetailInfo.getUsers().getAuths();
                Set<String> userauth = new HashSet<String>();
                authoritiesSet.forEach(auths -> userauth.add(auths.getAuthority()));
                model.put("userauth", userauth);

                return "admin/snipplets/div_user :: authedit";
//                break;
            default:
                redirectAttributesModelMap.addFlashAttribute("dto", dto);
                redirectAttributesModelMap.addFlashAttribute("userDetailInfo", userDetailInfo);
                return "redirect:/admin/user_ajax";
//                break;
        }
//        return "";
    }

    @RequestMapping("user_ajax")
//    @ResponseBody
    public void userManageAjax(@ModelAttribute("dto") BaseDTO dto, UserDetailInfo userDetailInfo, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();

        log.info("===userManageAjax logged ,the value is : {}", dto.toString());
        log.info("===userManageAjax logged ,the value is : {}", userDetailInfo.toString());
//        log.info("===userManage logged ,the value is : {}",userDetailInfo.getUsers().toString());


        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (dto.action) {
            case "add":
                if (userDetailInfoService.checkUsersExists(userDetailInfo)) {
                    log.info("===userManageAjax logged ,新用户已存在 : {}");
                    rtnData.put("error", "ERROR_UserExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "用户已经存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加用户");
                    userDetailInfoService.saveUserDetailInfo(userDetailInfo);
                }
                break;
            case "delete":
                boolean b = userDetailInfoService.deleteUsersByUsername(dto.sid);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除用户");
                } else {
                    rtnData.put("error", "ERROR_UserNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "用户不存在，删除失败");
                }
                break;
            case "update":
                userDetailInfoService.saveUserDetailInfo(userDetailInfo);
                rtnData.put("success1", "success!");
                break;
            case "list":
                List<Users> users = userDetailInfoService.listAllUsers();

//                return users;
//                    return mapper.writeValueAsString(users);
//                return EntityGsonHelper.goEntityWithCollection2Gson(Users.class);
                break;
            case "updateauth":
                RequestHelper.showParameters(request.getParameterMap());
                String[] authArrays = request.getParameterValues("authArray");
                String newauth = request.getParameter("newAuth");
                Map<String, Object> map = userDetailInfoService.updateAuths(userDetailInfo, authArrays, newauth);
                rtnData.putAll(map);

//                return users;
//                    return mapper.writeValueAsString(users);
//                return EntityGsonHelper.goEntityWithCollection2Gson(Users.class);
                break;
            default:

                break;
        }
//        return "";
        mapper.writeValue(response.getWriter(), rtnData);
    }

    @ModelAttribute("Department_list")
    public List<Department> Departmentlist() {
        return departmentRepository.findAll();
    }

}
