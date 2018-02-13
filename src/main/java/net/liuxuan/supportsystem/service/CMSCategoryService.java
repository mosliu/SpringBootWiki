package net.liuxuan.supportsystem.service;


import net.liuxuan.supportsystem.entity.CMSCategory;

import java.util.List;


/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.CMSCategoryService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/03/31 08:28
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-03-31  |    Moses        |     Created
 */
public interface CMSCategoryService {

    /**
     * Gets all department.
     *
     * @return the all department
     */

    void saveCMSCategory(CMSCategory cMSCategory);

    CMSCategory findCMSCategoryById(Long id);

    CMSCategory findCMSCategoryByName(String name);

    CMSCategory getOrCreateOneByName(String name);

    boolean deleteCMSCategoryById(Long id);

    boolean checkCMSCategoryExists(String cMSCategoryname);

    List<CMSCategory> getAllCMSCategory();

}