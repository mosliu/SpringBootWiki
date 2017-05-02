package net.liuxuan.SprKi.service;


import net.liuxuan.SprKi.entity.NewsPage;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.NewsPageService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/29 13:17
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-29  |    Moses        |     Created
*/
public interface NewsPageService {
    NewsPage saveNewsPage(NewsPage newsPage);

    NewsPage findById(Long id);

    boolean deleteNewsPageById(Long id);

//    boolean checkNewsPageExists(String newsPagename);

    List<NewsPage> getAllNewsPage();

}