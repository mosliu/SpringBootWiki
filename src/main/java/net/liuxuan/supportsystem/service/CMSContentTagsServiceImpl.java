package net.liuxuan.supportsystem.service;

import com.google.common.base.Strings;
import net.liuxuan.supportsystem.entity.CMSContentTags;
import net.liuxuan.supportsystem.repository.CMSContentTagsRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.CMSContentTagsServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/06/19 10:10
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-06-19  |    Moses        |     Created
 */
@Service
@Transactional
public class CMSContentTagsServiceImpl implements CMSContentTagsService {

    private static Logger log = LoggerFactory.getLogger(CMSContentTagsServiceImpl.class);

    /**
     * The Cms content tags repository.
     */
    @Autowired
    CMSContentTagsRepository cmsContentTagsRepository;

    @Override
    public void saveCMSContentTags(CMSContentTags contentTags) {
        cmsContentTagsRepository.save(contentTags);
    }

    @Override
    public CMSContentTags findCMSContentTagsById(Long id) {
        CMSContentTags cMSContentTags = cmsContentTagsRepository.findOne(id);
        return cMSContentTags;
    }

    @Override
    public boolean deleteCMSContentTagsById(Long id) {
        cmsContentTagsRepository.delete(id);
        return true;
//        CMSContentTags cMSContentTags = cmsContentTagsRepository.getOne(id);
//
//        if (cMSContentTags != null) {
//            cMSContentTags.setDisabled(true);
//            return true;
//        }
//        return false;
    }

    @Override
    public boolean checkCMSContentTagsExists(String cMSContentTagsname) {
        List<CMSContentTags> list = getCMSContentTagsByName(cMSContentTagsname);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CMSContentTags> getCMSContentTagsByName(String cmsContentTagsName) {
        return cmsContentTagsRepository.findByName(cmsContentTagsName);
    }

    @Override
    @Cacheable(cacheNames = "tag", key = "#cmsContentTagsName")
    public CMSContentTags getCMSContentTagByName(String cmsContentTagsName) {
        return cmsContentTagsRepository.findFirstByName(cmsContentTagsName);
    }

    @Override
    public List<CMSContentTags> getAllCMSContentTags() {
        return cmsContentTagsRepository.findAll();
    }

    @Override
    public List<String> getTagByNameLike(String tagName) {
        if(Strings.isNullOrEmpty(tagName)||tagName.trim().length()<2){
            return new ArrayList();
        }else{
            tagName = tagName.startsWith("%")?tagName:"%"+tagName;
            tagName = tagName.endsWith("%")?tagName:tagName+"%";
        }
        return cmsContentTagsRepository.findTagNamesByName(tagName);

    }
    @Override
    public Set<CMSContentTags> getTagSetFromTagStrArray(String[] tagStrs) {

        Set<CMSContentTags> rtnSet = new HashSet<>();
        if (tagStrs == null) {
            return null;
        } else {
//            List<CMSContentTags> tagsList = getAllCMSContentTags();

//            Map<String, CMSContentTags> tagsListMap = tagsList.stream().collect(Collectors.toMap(t -> t.getName(), p -> p));

            for (String tag : tagStrs) {
                CMSContentTags insertTag = null;
                if (StringUtils.isBlank(tag)) {
                    continue;
                } else {
                    tag = tag.trim();
                    insertTag = getCMSContentTagByName(tag);
                    if (insertTag == null) {
                        insertTag = new CMSContentTags();
                        insertTag.setName(tag);
                        insertTag.setTagtype("AUTOGENERATE");
                        cmsContentTagsRepository.save(insertTag);
                    }
                    rtnSet.add(insertTag);
                }
            }
            return rtnSet;
        }
    }

}