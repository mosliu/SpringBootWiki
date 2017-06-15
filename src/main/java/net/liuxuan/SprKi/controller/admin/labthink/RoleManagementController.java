package net.liuxuan.SprKi.controller.admin.labthink;

import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.security.Authorities;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity.security.Role;
import net.liuxuan.SprKi.entity.security.UrlAuth;
import net.liuxuan.SprKi.service.security.RoleService;
import net.liuxuan.SprKi.service.security.UrlAuthService;
import net.liuxuan.spring.Helper.RequestHelper;
import net.liuxuan.spring.Helper.ResponseHelper;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.admin.LabthinkBusinessManagementController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/3/21 10:16
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/3/21  |    Moses       |     Created
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleManagementController {
    private static Logger log = LoggerFactory.getLogger(RoleManagementController.class);

    @Autowired
    RoleService roleService;

    @Autowired
    UrlAuthService urlAuthService;

    @RequestMapping("roleManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "roleManage" + " :: middle";

    }

    @RequestMapping("role")
    public String roleManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===roleManage logged ,the _dto value is : {}", dto.toString());

        String id = dto.getSid();
        Role role;


        switch (dto.action) {
            case "edit":
                role = roleService.findRoleById(id);
                if (role != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("role", role);
                return "admin/snipplets/div_role :: roleedit";
            case "authedit":

                role = roleService.findRoleById(id);
                if(role==null){
                    model.put("error","无用户，请检查进入入口!!!!!!!!!!");
                }
                model.put("role", role);

                List<UrlAuth> urlAuths= urlAuthService.getAllUrlAuth();
                model.put("urlAuthsList", urlAuths);

                return "admin/snipplets/div_role :: urlAuthEdit";
//                break;
            default:
                return "redirect:/admin/role_ajax";
//                break;
        }
    }


    @RequestMapping("role_ajax")
//    @ResponseBody
    public void roleManageAjax(@ModelAttribute("dto") BaseDTO _dto, Role _role, HttpServletRequest request,
                               HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===roleManageAjax logged ,the value is : {}", _dto.toString());

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String role_name = request.getParameter("role_name");
                String describe = request.getParameter("describe");
                String comment = request.getParameter("comment");
                boolean roleExists = roleService.checkRoleExists(role_name);
                if (roleExists) {
                    log.info("===roleManageAjax logged ,添加角色已存在 : {}");
                    rtnData.put("error", "ERROR_RoleExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加角色已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加角色");
                    Role role = new Role();
                    role.setRolename(role_name);
                    role.setComment(comment);
                    role.setRoleDescribe(describe);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, role, "添加角色", "");
                    roleService.saveRole(role);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = roleService.deleteRoleById(_dto.sid);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除角色");
                } else {
                    rtnData.put("error", "ERROR_RoleNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "角色不存在，删除失败");
                }
                break;
            case "update":
                roleService.saveRole(_role);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _role, "更新角色", "");
                rtnData.put("success1", "success!");
                break;
            case "updateUrlAuth":

                RequestHelper.showParameters(request.getParameterMap());
                String[] authArrays = request.getParameterValues("authArray");
//                String newauth = request.getParameter("newAuth");
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, authArrays, "更新用户权限", "");

                String rolename = request.getParameter("rolename");

                Map<String, Object> map = roleService.updateAuths(rolename, authArrays);

                rtnData.putAll(map);
//
//                return users;
//                    return mapper.writeValueAsString(users);
//                return EntityGsonHelper.goEntityWithCollection2Gson(Users.class);
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMAPtoResponseAsJson(response, rtnData);
    }


    @ModelAttribute("Role_list")
    public List<Role> Rolelist() {
        return roleService.getAllRole();
    }
}
