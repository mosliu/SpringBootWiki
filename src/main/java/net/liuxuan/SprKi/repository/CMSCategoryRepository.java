package net.liuxuan.SprKi.repository;

import net.liuxuan.SprKi.entity.CMSCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.CMSCategoryRepository
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/15 13:40
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/15  |    Moses       |     Created
 */
public interface CMSCategoryRepository extends JpaRepository<CMSCategory, Long>, JpaSpecificationExecutor<CMSCategory> {
    List<CMSCategory> findByName(String  name);

    List<CMSCategory> findByNameNot(String  NotName);

    List<CMSCategory> findByNameNotOrderByName(String  NotName);


}
