package net.liuxuan.SprKi.controller.admin.labthink;

import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.service.labthink.DepartmentService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.admin.LabthinkBusinessManagementController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/14 10:16
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/14  |    Moses       |     Created
 */
@Controller
@RequestMapping("/admin")
public class DepartmentManagementController {
    private static Logger log = LoggerFactory.getLogger(DepartmentManagementController.class);

    @Autowired
    DepartmentService departmentService;

    @RequestMapping("departmentManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "departmentManage" + " :: middle";
    }

    @RequestMapping("department")
    public String departmentManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===departmentManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                Department department;
                Long id = dto.getStr2LongID();
                department = departmentService.getDepartmentById(id);
                if (department != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("department", department);
                return "admin/snipplets/div_department :: departmentedit";
            default:
                return "redirect:/admin/department_ajax";
//                break;
        }
    }


    @RequestMapping("department_ajax")
//    @ResponseBody
    public void departmentManageAjax(@ModelAttribute("dto") BaseDTO _dto, Department _department, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===departmentManageAjax logged ,the value is : {}", _dto.toString());

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String department_name = request.getParameter("department_name");
                String department_name_cn = request.getParameter("department_name_cn");
                boolean departmentexist = departmentService.checkDepartmentExists(department_name);
                if (departmentexist) {
                    log.info("===departmentManageAjax logged ,添加部门已存在 : {}");
                    rtnData.put("error", "ERROR_DepartmentExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加部门已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加部门");
                    Department department = new Department();
                    department.setDepartmentName(department_name);
                    department.setDepartmentNameCN(department_name);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, department, "添加部门", "");
                    departmentService.saveDepartment(department);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除部门", "");
                boolean b = departmentService.deleteDepartmentById(_dto.sid);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除部门");
                } else {
                    rtnData.put("error", "ERROR_UserNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "部门不存在，删除失败");
                }
                break;
            case "update":
                departmentService.saveDepartment(_department);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _department, "更新部门", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMAPtoResponseAsJson(response, rtnData);
    }


    @ModelAttribute("Department_list")
    public List<Department> Departmentlist() {
        return departmentService.getAllDepartment();
    }
}
