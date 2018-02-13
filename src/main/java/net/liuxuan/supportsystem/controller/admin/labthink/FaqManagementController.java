package net.liuxuan.supportsystem.controller.admin.labthink;

import net.liuxuan.spring.helper.ResponseHelper;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.supportsystem.entity.dto.FAQSearchDTO;
import net.liuxuan.supportsystem.entity.labthink.FAQContent;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.service.labthink.FAQContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class FaqManagementController {
    private static Logger log = LoggerFactory.getLogger(FaqManagementController.class);
    @Autowired
    private FAQContentService faqContentService;

    @RequestMapping("faqManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getFAQList(FAQSearchDTO dto, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.debug("===getFAQList logged ,the DTO value is : {}", dto);
//        log.debug("===getFAQList logged ,the isnull is : {}",dto.isAllNull());
        boolean dtoAllNull = dto.isAllNull();
        model.put("dtoNull", dtoAllNull);
        if (dtoAllNull) {
            //参数全为空
        }
        dto.setDisabled(true);

        List<FAQContent> allFAQContents = faqContentService.findAllFAQContentsByDto(dto);
        log.debug("faq list size is {}", allFAQContents.size());
        model.put("allfaqlist", allFAQContents);
        model.put("dto", dto);
        return "admin/" + "faqManage" + " :: middle";
    }

    @RequestMapping(value = "/faq/deleteforever/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteForEverFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> model) throws IOException {

        Map<String, Object> rtnData = new HashMap<String, Object>();
//        FAQContent faq = faqContentService.findById(id);
        SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.DELETE, faqContentService.findById(id), "彻底删除了文章", "/faq/show/" + id);
        faqContentService.deleteFAQContentById(id);
        rtnData.put("status", "success");
//        return "admin/" + "faqManage" + " :: middle";
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }

    @RequestMapping(value = "/faq/revert/{id}", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void revertFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
                            Map<String, Object> model) throws IOException {

        Map<String, Object> rtnData = new HashMap<String, Object>();
//        FAQContent faq = faqContentService.findById(id);
        SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ENABLE, faqContentService.findById(id), "回复了文章", "/faq/show/" + id);
        faqContentService.revertFAQContentById(id);
        rtnData.put("status", "success");
//        return "admin/" + "faqManage" + " :: middle";

        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


}
