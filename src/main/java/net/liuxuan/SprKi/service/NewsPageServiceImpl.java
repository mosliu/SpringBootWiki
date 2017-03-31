package net.liuxuan.SprKi.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.spring.Helper.bean.BeanHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.NewsPageRepository;
import net.liuxuan.SprKi.entity.NewsPage;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.NewsPageServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/29 13:17
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-29  |    Moses        |     Created
*/
@Service
@Transactional
public class NewsPageServiceImpl implements NewsPageService{

    private static Logger log = LoggerFactory.getLogger(NewsPageServiceImpl.class);

    @Autowired
    NewsPageRepository newsPageRepository;

    @Override
    public void saveNewsPage(NewsPage newsPage){
        DbUser u = ServiceHelper.getCurrentUsers();
        newsPage.setLastUpdateUser(u);
        Date now = new Date();
        newsPage.setLastUpdateDate(now);
        NewsPage load;
        if(newsPage.getId()==null){
            //新的
            log.trace("===saveNewsPage:new", newsPage);
            newsPage.setAuthor(u);
            newsPage.setPublishDate(now);
        }else if(!newsPageRepository.exists(newsPage.getId())){
            //新的
            log.trace("===saveNewsPage:new", newsPage);
            newsPage.setAuthor(u);
            newsPage.setPublishDate(now);
        }else{
            load = newsPageRepository.getOne(newsPage.getId());
            try {
                log.trace("===saveNewsPage logged ,the value Before COPY is : {}", newsPage);
                BeanHelper.copyWhenDestFieldNull(newsPage, load);
                log.trace("===saveNewsPage logged ,the value After  COPY is : {}", newsPage);
            } catch (InvocationTargetException e) {
                log.error("Copy faq error!", e);
//                e.printStackTrace();
            } catch (IllegalAccessException e) {
                log.error("Copy faq error!", e);
//                e.printStackTrace();
            }
        }

        newsPageRepository.save(newsPage);
    }

    @Override
    public NewsPage findNewsPageById(Long id){
        NewsPage newsPage = newsPageRepository.findOne(id);
        return newsPage;
    }

    @Override
    public boolean deleteNewsPageById(Long id){
        NewsPage newsPage = newsPageRepository.getOne(id);
        if (newsPage != null) {
            newsPage.setDisabled(true);
            return true;
        }
        return false;
    }

//    @Override
//    public boolean checkNewsPageExists(String newsPagename){
//        List<NewsPage> list = newsPageRepository.findByNewsPageName(newsPagename);
//        if (list.size() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public List<NewsPage> getAllNewsPage() {
        return newsPageRepository.findByDisabledFalse();
    }

}