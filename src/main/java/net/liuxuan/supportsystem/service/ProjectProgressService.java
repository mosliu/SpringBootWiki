package net.liuxuan.supportsystem.service;


import net.liuxuan.supportsystem.entity.ProjectProgress;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.ProjectProgressService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 14:59
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/
public interface ProjectProgressService {
    void saveProjectProgress(ProjectProgress projectProgress);

    ProjectProgress findProjectProgressById(Long id);

    boolean deleteProjectProgressById(Long id);

    boolean checkProjectProgressExists(String projectProgressname);

    List<ProjectProgress> getAllProjectProgress();

    List<ProjectProgress> getProjectProgressesByName(String projectProgressname);
}