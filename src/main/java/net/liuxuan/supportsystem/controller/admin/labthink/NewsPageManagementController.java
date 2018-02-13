package net.liuxuan.supportsystem.controller.admin.labthink;

import net.liuxuan.spring.helper.ResponseHelper;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.supportsystem.entity.NewsPage;
import net.liuxuan.supportsystem.entity.dto.BaseDTO;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.service.NewsPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.NewsPageRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/29 13:17
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-29  |    Moses        |     Created
*/

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class NewsPageManagementController {
    private static Logger log = LoggerFactory.getLogger(NewsPageManagementController.class);

    @Autowired
    NewsPageService newsPageService;

    @RequestMapping("newsPageManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {

        return "admin/" + "newsPageManage" + " :: middle";

    }

    @RequestMapping("newsPage")
    public String newsPageManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===newsPageManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                NewsPage newsPage;
                Long id = dto.getStr2LongID();
                
                newsPage = newsPageService.findById(id);
                if (newsPage != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("newsPage", newsPage);
                return "admin/snipplets/div_newsPage :: newsPageedit";
            default:
                return "redirect:/admin/newsPage_ajax";
//                break;
        }
    }


    @RequestMapping("newsPage_ajax")
//    @ResponseBody
    public void newsPageManageAjax(@ModelAttribute("dto") BaseDTO _dto, NewsPage _newsPage, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===newsPageManageAjax logged ,the value is : {}", _dto.toString());
        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
//                String newsPageName = request.getParameter("newsPageName");
//                String newsPageNameCN = request.getParameter("newsPageNameCN");
//                String comment = request.getParameter("comment");
//                boolean newsPageExists = newsPageService.checkNewsPageExists(newsPageName);
                boolean newsPageExists = false;
                if (newsPageExists) {
                    log.info("===newsPageManageAjax logged ,添加NewsPage已存在 : {}");
                    rtnData.put("error", "ERROR_NewsPageExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "添加NewsPage已存在");
                } else {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功添加NewsPage");
                    SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _newsPage, "添加角色", "");
                    newsPageService.saveNewsPage(_newsPage);
                }
                break;
            case "delete":
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
                boolean b = newsPageService.deleteNewsPageById(id);
                if (b) {
                    rtnData.put("status", "success");
                    rtnData.put("msg", "成功删除NewsPage");
                } else {
                    rtnData.put("error", "ERROR_NewsPageNotExists");
                    rtnData.put("status", "fail");
                    rtnData.put("msg", "NewsPage不存在，删除失败");
                }
                break;
            case "update":
                newsPageService.saveNewsPage(_newsPage);
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _newsPage, "更新NewsPage", "");
                rtnData.put("success1", "success!");
                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("NewsPage_list")
    public List<NewsPage> NewsPagelist() {
        return newsPageService.getAllNewsPage();
    }

    /**
     * Init binder.
     *
     * @param binder the binder
     * @throws ServletException the servlet exception
     */
    @InitBinder
    protected void initBinder(
            WebDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//        binder.registerCustomEditor(CMSCategory.class,cmsCategoryEditor());
//        binder.registerCustomEditor(Department.class,new DepartmentEditor());
    }
}