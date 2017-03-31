package net.liuxuan.SprKi.repository;

import java.util.List;
import net.liuxuan.SprKi.entity.ProjectProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.ProjectProgressRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/27 14:59
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-27  |    Moses        |     Created
*/

public interface ProjectProgressRepository extends JpaRepository<ProjectProgress, Long>, JpaSpecificationExecutor<ProjectProgress> {
    List<ProjectProgress> findByProjectProgressName(String  name);

    List<ProjectProgress> findByProjectProgressNameNot(String  NotName);
    List<ProjectProgress> findByDisabledFalse();

    List<ProjectProgress> findByProjectProgressNameNotOrderByProjectProgressName(String roleNotName);

}


