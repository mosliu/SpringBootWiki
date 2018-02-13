package net.liuxuan.supportsystem.controller.news;

import net.liuxuan.supportsystem.entity.NewsPage;
import net.liuxuan.supportsystem.exceptions.ContentNotFoundException;
import net.liuxuan.supportsystem.service.NewsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.news.NewsPageController
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


    @RequestMapping("/news2")
    public String news2(HttpServletRequest request, Map<String, Object> model) {
        List<NewsPage> newsPageList = newsPageService.getAllNewsPage();
        model.put("newsList",newsPageList);
        return "news/news_index";
    }
    @RequestMapping("/news")
    public String news(HttpServletRequest request,
                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "20") Integer size,
                       HttpServletResponse response,Map<String, Object> model) {
        Page<NewsPage> newsPageList = newsPageService.getAllNewsPageable(page,size);
        //分页 必须用datas 或者改页面标签
        model.put("datas",newsPageList);
        return "news/news_index";
    }

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
