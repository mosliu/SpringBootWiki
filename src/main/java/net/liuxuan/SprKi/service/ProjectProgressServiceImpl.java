package net.liuxuan.SprKi.service;

import java.util.List;

import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.ProjectProgressRepository;
import net.liuxuan.SprKi.entity.ProjectProgress;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.ProjectProgressServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 14:59
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/
@Service
@Transactional
public class ProjectProgressServiceImpl implements ProjectProgressService{

    private static Logger log = LoggerFactory.getLogger(ProjectProgressServiceImpl.class);

    @Autowired
    ProjectProgressRepository projectProgressRepository;

    @Override
    @CachePut(cacheNames = "projectProgress",key = "#projectProgress.id")
    public void saveProjectProgress(ProjectProgress projectProgress){
        projectProgressRepository.save(projectProgress);
    }

    @Override
    @Cacheable(cacheNames = "projectProgress",key = "#id")
    public ProjectProgress findProjectProgressById(Long id){
        ProjectProgress projectProgress = projectProgressRepository.findOne(id);
        return projectProgress;
    }

    @Override
    @CacheEvict(cacheNames = "projectProgress",key = "#id")
    public boolean deleteProjectProgressById(Long id){
        ProjectProgress projectProgress = projectProgressRepository.getOne(id);
        if (projectProgress != null) {
//            projectProgress.setDisabled(true);
            projectProgressRepository.delete(projectProgress);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkProjectProgressExists(String projectProgressname){
        List<ProjectProgress> list = getProjectProgressesByName(projectProgressname);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Cacheable(cacheNames = "projectProgress",key = "#projectProgressname")
    public List<ProjectProgress> getProjectProgressesByName(String projectProgressname) {
        return projectProgressRepository.findByProjectProgressName(projectProgressname);
    }

    @Override
    @Cacheable(cacheNames = "projectProgress", key = "'projectProgress_list'")
    public List<ProjectProgress> getAllProjectProgress() {
        return projectProgressRepository.findByDisabledFalse();
    }

}