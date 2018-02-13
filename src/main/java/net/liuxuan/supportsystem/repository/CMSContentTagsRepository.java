package net.liuxuan.supportsystem.repository;

import net.liuxuan.supportsystem.entity.CMSContentTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.CMSContentTagsRepository
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/15 13:44
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/15  |    Moses       |     Created
 */
public interface CMSContentTagsRepository extends JpaRepository<CMSContentTags, Long>, JpaSpecificationExecutor<CMSContentTags> {
    List<CMSContentTags> findByName(String  name);
    CMSContentTags findFirstByName(String name);

    @Query("select name from CMSContentTags tag where tag.name like ?1")
    List<String> findTagNamesByName(String name);

}
