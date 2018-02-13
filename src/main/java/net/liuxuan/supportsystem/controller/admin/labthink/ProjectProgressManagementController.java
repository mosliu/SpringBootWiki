package net.liuxuan.supportsystem.controller.admin.labthink;

import net.liuxuan.supportsystem.entity.DTO.BaseDTO;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.entity.ProjectProgress;
import net.liuxuan.supportsystem.service.ProjectProgressService;
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
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.ProjectProgressRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 14:59
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProjectProgressManagementController {
    private static Logger log = LoggerFactory.getLogger(ProjectProgressManagementController.class);

    @Autowired
    ProjectProgressService projectProgressService;

    @RequestMapping("projectProgressManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "projectProgressManage" + " :: middle";

    }

    @RequestMapping("projectProgress")
    public String projectProgressManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===projectProgressManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                ProjectProgress projectProgress;
                Long id = dto.getStr2LongID();
                
                projectProgress = projectProgressService.findProjectProgressById(id);
                if (projectProgress != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("projectProgress", projectProgress);
                return "admin/snipplets/div_projectProgress :: projectProgressedit";
            default:
                return "redirect:/admin/projectProgress_ajax";
//                break;
        }
    }


    @RequestMapping("projectProgress_ajax")
//    @ResponseBody
    public void projectProgressManageAjax(@ModelAttribute("dto") BaseDTO _dto, ProjectProgress _projectProgress, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===projectProgressManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String projectProgressName = request.getParameter("projectProgressName");
                String projectProgressNameCN = request.getParameter("projectProgressNameCN");
                String comment = request.getParameter("comment");
                boolean projectProgressExists = projectProgressService.checkProjectProgressExists(projectProgressName);
                if (projectProgressExists) {
                    log.info("===projectProgressManageAjax logged ,添加ProjectProgress已存在 : {}");
                    rtnData.put("error", "ERROR_ProjectProgressExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加ProjectProgress已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加ProjectProgress");
//                    ProjectProgress projectProgress = new ProjectProgress();
//                    projectProgress.setProjectProgressName(projectProgressName);
//                    projectProgress.setComment(comment);
//                    projectProgress.setProjectProgressNameCN(projectProgressNameCN);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _projectProgress, "添加项目进度", "");
                    projectProgressService.saveProjectProgress(_projectProgress);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除项目进度", "");
                boolean b = projectProgressService.deleteProjectProgressById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除ProjectProgress");
                } else {
                    rtnData.put("error", "ERROR_ProjectProgressNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "ProjectProgress不存在，删除失败");
                }
                break;
            case "update":
                projectProgressService.saveProjectProgress(_projectProgress);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _projectProgress, "更新ProjectProgress", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("ProjectProgress_list")
    public List<ProjectProgress> ProjectProgresslist() {
        return projectProgressService.getAllProjectProgress();
    }
}