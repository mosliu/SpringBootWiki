package net.liuxuan.SprKi.controller.admin.labthink;

import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity.CMSCategory;
import net.liuxuan.SprKi.service.CMSCategoryService;
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
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.CMSCategoryRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/31 08:28
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-31  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CMSCategoryManagementController {
    private static Logger log = LoggerFactory.getLogger(CMSCategoryManagementController.class);

    @Autowired
    CMSCategoryService cmsCategoryService;

    @RequestMapping("cmsCategoryManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "cmsCategoryManage" + " :: middle";

    }

    @RequestMapping("cmsCategory")
    public String cmsCategoryManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===cmsCategoryManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                CMSCategory cmsCategory;
                Long id = dto.getStr2LongID();
                
                cmsCategory = cmsCategoryService.findCMSCategoryById(id);
                if (cmsCategory != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("cmsCategory", cmsCategory);
                return "admin/snipplets/div_cmsCategory :: cmsCategoryedit";
            default:
                return "redirect:/admin/cmsCategory_ajax";
//                break;
        }
    }


    @RequestMapping("cmsCategory_ajax")
//    @ResponseBody
    public void cmsCategoryManageAjax(@ModelAttribute("dto") BaseDTO _dto, CMSCategory _cmsCategory, HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===cmsCategoryManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String cmsCategoryName = request.getParameter("cmsCategoryName");
                String cmsCategoryNameCN = request.getParameter("cmsCategoryNameCN");
                String comment = request.getParameter("comment");
                boolean cmsCategoryExists = cmsCategoryService.checkCMSCategoryExists(cmsCategoryName);
                if (cmsCategoryExists) {
                    log.info("===cmsCategoryManageAjax logged ,添加CMSCategory已存在 : {}");
                    rtnData.put("error", "ERROR_CMSCategoryExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加CMSCategory已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加CMSCategory");
//                    CMSCategory cmsCategory = new CMSCategory();
//                    cmsCategory.setCMSCategoryName(cmsCategoryName);
//                    cmsCategory.setComment(comment);
//                    cmsCategory.setCMSCategoryNameCN(cmsCategoryNameCN);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _cmsCategory, "添加角色", "");
                    cmsCategoryService.saveCMSCategory(_cmsCategory);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = cmsCategoryService.deleteCMSCategoryById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除CMSCategory");
                } else {
                    rtnData.put("error", "ERROR_CMSCategoryNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "CMSCategory不存在，删除失败");
                }
                break;
            case "update":
                cmsCategoryService.saveCMSCategory(_cmsCategory);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _cmsCategory, "更新CMSCategory", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMAPtoResponseAsJson(response, rtnData);
    }


    @ModelAttribute("CMSCategory_list")
    public List<CMSCategory> CMSCategorylist() {
        return cmsCategoryService.getAllCMSCategory();
    }
}