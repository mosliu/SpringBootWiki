package net.liuxuan.SprKi.service;

import java.util.List;

import net.liuxuan.spring.constants.JPAConstants;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.transform.impl.InterceptFieldCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.CMSCategoryRepository;
import net.liuxuan.SprKi.entity.CMSCategory;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.CMSCategoryServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/31 08:28
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-31  |    Moses        |     Created
*/
@Service
@Transactional
public class CMSCategoryServiceImpl implements CMSCategoryService{

    private static Logger log = LoggerFactory.getLogger(CMSCategoryServiceImpl.class);

    @Autowired
    CMSCategoryRepository cmsCategoryRepository;

    @Override
    public void saveCMSCategory(CMSCategory cmsCategory){
        cmsCategoryRepository.save(cmsCategory);
    }

    @Override
    @Cacheable(cacheNames = "category",key="#id")
    public CMSCategory findCMSCategoryById(Long id){
        CMSCategory cmsCategory = cmsCategoryRepository.findOne(id);
        return cmsCategory;
    }

    @Override
    @Cacheable(cacheNames = "category",key="#name")
    public CMSCategory findCMSCategoryByName(String name){
        List<CMSCategory> cmsCategoryList = cmsCategoryRepository.findByName(name);
        if(cmsCategoryList==null||cmsCategoryList.size()==0){
            return null;
        }else{
            return cmsCategoryList.get(0);
        }
//        CMSCategory cmsCategory = cmsCategoryRepository.findOne(id);
//        return cmsCategory;
    }

    /**
     * find one if exist Or create one if null.
     * @param name
     * @return
     */
    @Override
//    @CachePut(cacheNames = "category",key="#name")
    public CMSCategory getOrCreateOneByName(String name){
        CMSCategory category = findCMSCategoryByName(name);
        if (category==null) {
            category = new CMSCategory();
            category.setDisabled(false);
            category.setEnglishName(name);
            category.setName(name);
            cmsCategoryRepository.save(category);
        }
        return category;
    }

    @Override
    @CacheEvict(cacheNames = "category",key="#id")
    public boolean deleteCMSCategoryById(Long id){
        CMSCategory cmsCategory = cmsCategoryRepository.getOne(id);
        if (cmsCategory != null) {
            cmsCategory.setDisabled(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkCMSCategoryExists(String cmsCategoryname){
        return findCMSCategoryByName(cmsCategoryname)!=null;
    }

    @Override
    @Cacheable(cacheNames = "category", key = "'category_list'")
    public List<CMSCategory> getAllCMSCategory() {
        return cmsCategoryRepository.findByNameNotOrderByName(JPAConstants.DELETEDOBJECTSTR);
//        return cmsCategoryRepository.findByDisabledFalse();
    }

}