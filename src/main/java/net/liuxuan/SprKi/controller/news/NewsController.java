package net.liuxuan.SprKi.controller.news;

import net.liuxuan.SprKi.entity.NewsPage;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.entity.security.LogActionType;
import net.liuxuan.SprKi.exceptions.ContentNotFoundException;
import net.liuxuan.SprKi.service.NewsPageService;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.news.NewsController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/4/5 9:08
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/4/5  |    Moses       |     Created
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class NewsController {

    @Autowired
    NewsPageService newsPageService;

    @RequestMapping(value = "/news/show/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String showFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        //TODO 全部返回，有所不对。应做分页展示。
        NewsPage obj = newsPageService.findById(id);
        if (obj == null) {
            throw new ContentNotFoundException("", id);
        }
        model.put("news", obj);
//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "news/news_show";
    }
}
