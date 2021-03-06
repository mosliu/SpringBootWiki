package net.liuxuan.supportsystem.repository;

import net.liuxuan.supportsystem.entity.CMSContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.CMSContentRepository
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/15 13:43
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/15  |    Moses       |     Created
 */
public interface CMSContentRepository extends JpaRepository<CMSContent, Long>, JpaSpecificationExecutor<CMSContent> {
}
