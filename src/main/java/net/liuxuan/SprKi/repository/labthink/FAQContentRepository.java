package net.liuxuan.SprKi.repository.labthink;

import net.liuxuan.SprKi.entity.CMSContentTags;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.labthink.FAQContentRepository
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 16:24
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/16  |    Moses       |     Created
 */
public interface FAQContentRepository extends JpaRepository<FAQContent, Long>, JpaSpecificationExecutor<FAQContent> {
}
