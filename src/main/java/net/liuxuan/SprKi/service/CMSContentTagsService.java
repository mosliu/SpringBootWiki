package net.liuxuan.SprKi.service;


import net.liuxuan.SprKi.entity.CMSContentTags;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;


/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.CMSContentTagsService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/06/19 10:10
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-06-19  |    Moses        |     Created
 */
public interface CMSContentTagsService {
    /**
     * Save cms content tags.
     *
     * @param contentTags the content tags
     */
    void saveCMSContentTags(CMSContentTags contentTags);

    /**
     * Find cms content tags by id cms content tags.
     *
     * @param id the id
     * @return the cms content tags
     */
    CMSContentTags findCMSContentTagsById(Long id);

    /**
     * Delete cms content tags by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteCMSContentTagsById(Long id);

    /**
     * Check cms content tags exists boolean.
     *
     * @param cMSContentTagsname the c ms content tagsname
     * @return the boolean
     */
    boolean checkCMSContentTagsExists(String cMSContentTagsname);

    /**
     * Gets cms content tag List by name.
     *
     * @param cMSContentTagsname the c ms content tagsname
     * @return the cms content tags by name
     */
    List<CMSContentTags> getCMSContentTagsByName(String cMSContentTagsname);

    /**
     * Gets cms content tag by name.
     *
     * @param cmsContentTagsName the cms content tags name
     * @return the cms content tag by name
     */
    CMSContentTags getCMSContentTagByName(String cmsContentTagsName);

    /**
     * Gets all cms content tags.
     *
     * @return the all cms content tags
     */
    List<CMSContentTags> getAllCMSContentTags();

    /**
     * Gets tag by name like.
     *
     * @param tagName the tag name
     * @return the tag by name like
     */
    List<String> getTagByNameLike(String tagName);

    /**
     * Get tag set from tag str array set [ ].
     *
     * @param tagStrs the tag strs
     * @return the set [ ]
     */
    Set<CMSContentTags> getTagSetFromTagStrArray(String[] tagStrs);
}