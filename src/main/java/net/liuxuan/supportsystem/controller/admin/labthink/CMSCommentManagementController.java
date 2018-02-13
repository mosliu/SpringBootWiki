package net.liuxuan.supportsystem.controller.admin.labthink;

import net.liuxuan.spring.helper.ResponseHelper;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.supportsystem.entity.CMSComment;
import net.liuxuan.supportsystem.entity.dto.BaseDTO;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.service.CMSCommentService;
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
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.CMSCommentRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/06/21 14:36
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-06-21  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
public class CMSCommentManagementController {
    private static Logger log = LoggerFactory.getLogger(CMSCommentManagementController.class);

    @Autowired
    private CMSCommentService cMSCommentService;

    @RequestMapping("cMSCommentManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "cMSCommentManage" + " :: middle";

    }

    @RequestMapping("cMSComment")
    public String cMSCommentManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===cMSCommentManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                CMSComment cMSComment;
                Long id = dto.getStr2LongID();
                
                cMSComment = cMSCommentService.findCMSCommentById(id);
                if (cMSComment != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("cMSComment", cMSComment);
                return "admin/snipplets/div_cMSComment :: cMSCommentedit";
            default:
                return "redirect:/admin/cMSComment_ajax";
//                break;
        }
    }


    @RequestMapping("cMSComment_ajax")
//    @ResponseBody
    public void cMSCommentManageAjax(@ModelAttribute("dto") BaseDTO _dto, CMSComment _cMSComment, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===cMSCommentManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String cMSCommentName = request.getParameter("cMSCommentName");
                String cMSCommentNameCN = request.getParameter("cMSCommentNameCN");
                String comment = request.getParameter("comment");
//                boolean cMSCommentExists = cMSCommentService.checkCMSCommentExists(cMSCommentName);
//                if (cMSCommentExists) {
//                    log.info("===cMSCommentManageAjax logged ,添加CMSComment已存在 : {}");
//                    rtnData.put("error", "ERROR_CMSCommentExists");
//                    rtnData.put("status", "fail");
//                    rtnData.put("msg", "添加CMSComment已存在");
//                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加CMSComment");
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _cMSComment, "添加角色", "");
                    cMSCommentService.saveCMSComment(_cMSComment);
//                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = cMSCommentService.deleteCMSCommentById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除CMSComment");
                } else {
                    rtnData.put("error", "ERROR_CMSCommentNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "CMSComment不存在，删除失败");
                }
                break;
            case "update":
                cMSCommentService.saveCMSComment(_cMSComment);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _cMSComment, "更新CMSComment", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("CMSComment_list")
    public List<CMSComment> CMSCommentlist() {
        return cMSCommentService.getAllCMSComment();
    }
}