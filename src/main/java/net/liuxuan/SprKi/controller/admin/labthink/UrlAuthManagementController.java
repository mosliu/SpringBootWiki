package net.liuxuan.SprKi.controller.admin.labthink;

import net.liuxuan.SprKi.entity.DTO.BaseDTO;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.entity.security.UrlAuth;
import net.liuxuan.SprKi.service.security.UrlAuthService;
import net.liuxuan.spring.Helper.ResponseHelper;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.apache.commons.lang3.StringUtils;
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
public class UrlAuthManagementController {
    private static Logger log = LoggerFactory.getLogger(UrlAuthManagementController.class);

    @Autowired
    UrlAuthService urlAuthService;

    @RequestMapping("urlAuthManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "urlAuthManage" + " :: middle";

    }

    @RequestMapping("urlAuth")
    public String urlAuthManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===urlAuthManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                UrlAuth urlAuth;
                Long id = dto.getStr2LongID();

                urlAuth = urlAuthService.findUrlAuthById(id);
                if (urlAuth != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("urlAuth", urlAuth);
                return "admin/snipplets/div_urlAuth :: urlAuthedit";
            default:
                return "redirect:/admin/urlAuth_ajax";
//                break;
        }
    }


    @RequestMapping("urlAuth_ajax")
//    @ResponseBody
    public void urlAuthManageAjax(@ModelAttribute("dto") BaseDTO _dto, UrlAuth _urlAuth, HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===urlAuthManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String urlAuthName = request.getParameter("urlAuthName");

                if(StringUtils.isBlank(urlAuthName)){
                    log.info("===传入参数错误！！！");
                    rtnData.put("error", "ERROR_ParamsInputError");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "传入参数错误");
                    break;
                }
                urlAuthName = urlAuthName.toLowerCase();
                if (!urlAuthName.startsWith("/")) {
                    urlAuthName = "/" + urlAuthName;
                }

                String urlAuthNameCN = request.getParameter("urlAuthNameCN");
                String comment = request.getParameter("comment");
                urlAuthName = urlAuthName==null?"":urlAuthName;
                comment = comment==null?"":comment;

                boolean urlAuthExists = urlAuthService.checkUrlAuthExists(urlAuthName);
                if (urlAuthExists) {
                    log.info("===urlAuthManageAjax logged ,添加Url权限已存在 : {}");
                    rtnData.put("error", "ERROR_UrlAuthExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加UrlAuth已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加UrlAuth");
                    UrlAuth urlAuth = new UrlAuth();
                    urlAuth.setUrlAuthName(urlAuthName);
                    urlAuth.setComment(comment);
                    urlAuth.setUrlAuthNameCN(urlAuthNameCN);
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, urlAuth, "添加角色", "");
                    urlAuthService.saveUrlAuth(urlAuth);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = urlAuthService.deleteUrlAuthById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除UrlAuth");
                } else {
                    rtnData.put("error", "ERROR_UrlAuthNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "UrlAuth不存在，删除失败");
                }
                break;
            case "update":
                urlAuthService.saveUrlAuth(_urlAuth);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _urlAuth, "更新角色", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMAPtoResponseAsJson(response, rtnData);
    }


    @ModelAttribute("UrlAuth_list")
    public List<UrlAuth> UrlAuthlist() {
        return urlAuthService.getAllUrlAuth();
    }
}
