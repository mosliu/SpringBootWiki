package net.liuxuan.SprKi.service;

import net.liuxuan.SprKi.entity.NewsPage;
import net.liuxuan.SprKi.entity.ProjectProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.PrinterJob;
import java.util.List;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.IndexServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/3/27 16:22
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/3/27  |    Moses       |     Created
 */
@Service
@Transactional
public class IndexServiceImpl implements IndexService {
    @Autowired
    ProjectProgressService projectProgressService;

    @Autowired
    NewsPageService newsPageService;

    @Override
    public List<ProjectProgress> getProjectProgressList() {
        return projectProgressService.getAllProjectProgress();
    }

    @Override
    public List<NewsPage> getNewsPageList() {
        return newsPageService.getAllNewsPage();
    }
}
