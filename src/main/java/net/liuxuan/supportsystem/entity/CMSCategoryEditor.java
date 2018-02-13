package net.liuxuan.supportsystem.entity;

import net.liuxuan.supportsystem.repository.CMSCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyEditorSupport;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.CMSCategoryEditor
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 10:03
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/16  |    Moses       |     Created
 */

public class CMSCategoryEditor extends PropertyEditorSupport {
    private static Logger log =  LoggerFactory.getLogger(CMSCategoryEditor.class);

    @Autowired

    private CMSCategoryRepository cmsCategoryRepository;



    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null) {
            log.debug("===========cmsCategoryRepository is : {}",cmsCategoryRepository);
            CMSCategory cmsCategory = cmsCategoryRepository.findOne(Long.valueOf(text));

            log.debug("===========cmsCategory is : {}",cmsCategory);
            setValue(cmsCategory);
        } else {
            setValue(null);
        }
    }
}
