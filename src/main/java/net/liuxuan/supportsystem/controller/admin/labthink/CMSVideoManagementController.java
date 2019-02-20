package net.liuxuan.supportsystem.controller.admin.labthink;

import net.liuxuan.spring.helper.ResponseHelper;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.supportsystem.entity.CMSVideo;
import net.liuxuan.supportsystem.entity.dto.BaseDTO;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.service.CMSVideoService;
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
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.CMSVideoRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/09/29 13:52
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-09-29  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
public class CMSVideoManagementController {
    private static Logger log = LoggerFactory.getLogger(CMSVideoManagementController.class);

    @Autowired
    private CMSVideoService cmsVideoService;

    @RequestMapping("CMSVideoManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "CMSVideoManage" + " :: middle";

    }

    @RequestMapping("CMSVideo")
    public String cmsVideoManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===cmsVideoManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                CMSVideo cmsVideo;
                Long id = dto.getStr2LongID();
                
                cmsVideo = cmsVideoService.findCMSVideoById(id);
                if (cmsVideo != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("cmsVideo", cmsVideo);
                return "admin/snipplets/div_CMSVideo :: cmsVideoEdit";
            default:
                return "redirect:/admin/CMSVideo_ajax";
//                break;
        }
    }


    @RequestMapping("CMSVideo_ajax")
//    @ResponseBody
    public void cmsVideoManageAjax(@ModelAttribute("dto") BaseDTO _dto, CMSVideo _cmsVideo, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===cmsVideoManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String cmsVideoName = request.getParameter("cmsVideoName");
//                String cmsVideoFilepath = request.getParameter("cmsVideoFilepath");
//                String comment = request.getParameter("comment");
                boolean cmsVideoExists = cmsVideoService.checkCMSVideoExists(cmsVideoName);
                if (cmsVideoExists) {
                    log.info("===cmsVideoManageAjax logged ,添加CMSVideo已存在 : {}");
                    rtnData.put("error", "ERROR_CMSVideoExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加CMSVideo已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加CMSVideo");
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _cmsVideo, "添加角色", "");
                    cmsVideoService.saveCMSVideo(_cmsVideo);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = cmsVideoService.deleteCMSVideoById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除CMSVideo");
                } else {
                    rtnData.put("error", "ERROR_CMSVideoNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "CMSVideo不存在，删除失败");
                }
                break;
            case "update":
                cmsVideoService.saveCMSVideo(_cmsVideo);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _cmsVideo, "更新CMSVideo", "");
                rtnData.put("status", "success");
                rtnData.put("msg", "更新成功");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("CMSVideo_list")
    public List<CMSVideo> CMSVideolist() {
        return cmsVideoService.getAllCMSVideo();
    }
}