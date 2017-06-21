package net.liuxuan.SprKi.controller.admin.labthink;

import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity.CMSContentTags;
import net.liuxuan.SprKi.service.CMSContentTagsService;
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
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.CMSContentTagsRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/06/19 10:10
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-06-19  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
public class CMSContentTagsManagementController {
    private static Logger log = LoggerFactory.getLogger(CMSContentTagsManagementController.class);

    @Autowired
    CMSContentTagsService cMSContentTagsService;

    @RequestMapping("cMSContentTagsManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "cMSContentTagsManage" + " :: middle";

    }

    @RequestMapping("cMSContentTags")
    public String cMSContentTagsManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===cMSContentTagsManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                CMSContentTags cMSContentTags;
                Long id = dto.getStr2LongID();
                
                cMSContentTags = cMSContentTagsService.findCMSContentTagsById(id);
                if (cMSContentTags != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("cMSContentTags", cMSContentTags);
                return "admin/snipplets/div_cMSContentTags :: cMSContentTagsedit";
            default:
                return "redirect:/admin/cMSContentTags_ajax";
//                break;
        }
    }


    @RequestMapping("cMSContentTags_ajax")
//    @ResponseBody
    public void cMSContentTagsManageAjax(@ModelAttribute("dto") BaseDTO _dto, CMSContentTags _cMSContentTags, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===cMSContentTagsManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String cMSContentTagsName = request.getParameter("cMSContentTagsName");
                String cMSContentTagsNameCN = request.getParameter("cMSContentTagsNameCN");
                String comment = request.getParameter("comment");
                boolean cMSContentTagsExists = cMSContentTagsService.checkCMSContentTagsExists(cMSContentTagsName);
                if (cMSContentTagsExists) {
                    log.info("===cMSContentTagsManageAjax logged ,添加CMSContentTags已存在 : {}");
                    rtnData.put("error", "ERROR_CMSContentTagsExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加CMSContentTags已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加CMSContentTags");
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _cMSContentTags, "添加角色", "");
                    cMSContentTagsService.saveCMSContentTags(_cMSContentTags);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = cMSContentTagsService.deleteCMSContentTagsById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除CMSContentTags");
                } else {
                    rtnData.put("error", "ERROR_CMSContentTagsNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "CMSContentTags不存在，删除失败");
                }
                break;
            case "update":
                cMSContentTagsService.saveCMSContentTags(_cMSContentTags);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _cMSContentTags, "更新CMSContentTags", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("CMSContentTags_list")
    public List<CMSContentTags> CMSContentTagslist() {
        return cMSContentTagsService.getAllCMSContentTags();
    }
}