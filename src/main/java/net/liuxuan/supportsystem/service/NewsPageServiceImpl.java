package net.liuxuan.supportsystem.service;

import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.spring.helper.bean.BeanHelper;
import net.liuxuan.supportsystem.entity.NewsPage;
import net.liuxuan.supportsystem.entity.security.DbUser;
import net.liuxuan.supportsystem.repository.NewsPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

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
public class NewsPageServiceImpl implements NewsPageService {

    public static final String NEWSPAGECATEGORY = NewsPage.class.getSimpleName();
    private static Logger log = LoggerFactory.getLogger(NewsPageServiceImpl.class);
    @Autowired
    private NewsPageRepository newsPageRepository;
    @Autowired
    private CMSCategoryService cmsCategoryService;


    @Override
    @CacheEvict(cacheNames = "NewsPage", allEntries = true)
//    @CachePut(cacheNames = "NewsPage", key = "#newsPage.id", condition = "#newsPage.id != null")
    public NewsPage saveNewsPage(NewsPage newsPage) {
        DbUser u = SystemHelper.getCurrentUser();
        newsPage.setLastUpdateUser(u);
        Date now = new Date();
        newsPage.setLastUpdateDate(now);
        NewsPage load;
        if (newsPage.getId() == null || !newsPageRepository.exists(newsPage.getId())) {
            //新的
            log.trace("===saveNewsPage:new", newsPage);
            newsPage.setAuthor(u);
            newsPage.setPublishDate(now);
            newsPage.setTitle(newsPage.getFullTitle());
            newsPage.setCategory(cmsCategoryService.getOrCreateOneByName(NEWSPAGECATEGORY));
        } else {
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

        return newsPageRepository.save(newsPage);

    }

    @Override
    @Cacheable(cacheNames = "NewsPage", key = "#p0")
    public NewsPage findById(Long id) {
        NewsPage newsPage = newsPageRepository.findOne(id);
        return newsPage;
    }

    @Override
    @CacheEvict(cacheNames = "NewsPage", allEntries = true)
    public boolean deleteNewsPageById(Long id) {
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
    @Cacheable(cacheNames = "NewsPage", key = "'list'")
    public List<NewsPage> getAllNewsPage() {
//        return newsPageRepository.findByDisabledFalse();
        return newsPageRepository.findByDisabledFalseOrderByShowDateDesc();
    }

    @Override
    @Cacheable(cacheNames = "NewsPage", key = "'listtop20'")
    public List<NewsPage> getTop20NewsPage() {
//        return newsPageRepository.findTop20ByDisabledFalseOrderByLastUpdateDateDesc();
        return newsPageRepository.findTop20ByDisabledFalseOrderByShowDateDescLastUpdateDateDesc();
    }

    @Override
    @Cacheable(cacheNames = "NewsPage", key = "'pageable'+#page+':' + #size")
    public Page<NewsPage> getAllNewsPageable(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "LastUpdateDate");
        return newsPageRepository.findAllByDisabledFalseOrderByShowDateDesc(pageable);
    }
}