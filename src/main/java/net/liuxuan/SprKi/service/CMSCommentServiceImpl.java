package net.liuxuan.SprKi.service;

import java.util.Date;
import java.util.List;

import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.service.user.UserDetailInfoService;
import net.liuxuan.spring.Helper.SystemHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import net.liuxuan.SprKi.repository.CMSCommentRepository;
import net.liuxuan.SprKi.entity.CMSComment;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.CMSCommentServiceImpl
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/06/21 14:36
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-06-21  |    Moses        |     Created
*/
@Service
@Transactional
public class CMSCommentServiceImpl implements CMSCommentService{

    private static Logger log = LoggerFactory.getLogger(CMSCommentServiceImpl.class);

    @Autowired
    CMSCommentRepository cmsCommentRepository;

    @Autowired
    UserDetailInfoService userDetailInfoService;

    @Override
//    @Cacheable(cacheNames = "comment", key = "#cmsComment.id")
    @CacheEvict(cacheNames = "faqContent", key="#cmsComment.content.id")
    public CMSComment saveCMSComment(CMSComment cmsComment){
        UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();

        cmsComment.setAuthor(currentUserDetailInfo);
        Date now = new Date();
        cmsComment.setPublishDate(now);
        cmsComment.setCommentIP(SystemHelper.getCurrentUserIp());
        cmsComment =  cmsCommentRepository.save(cmsComment);
        return cmsComment;
    }

    @Override
    @Cacheable(cacheNames = "comment", key = "#id")
    public CMSComment findCMSCommentById(Long id){
        CMSComment cmsComment = cmsCommentRepository.findOne(id);
        return cmsComment;
    }

    @Override
    @CacheEvict(cacheNames = "comment", key = "#id")
    public boolean deleteCMSCommentById(Long id){
        cmsCommentRepository.delete(id);
//        CMSComment cmsComment = cmsCommentRepository.getOne(id);
//        if (cmsComment != null) {
//            cmsComment.setDisabled(true);
//            return true;
//        }
//        return false;
        return true;
    }

//    @Override
//    public boolean checkCMSCommentExists(String cmsCommentname){
//        List<CMSComment> list = getCMSCommentByName(cmsCommentname);
//        if (list.size() > 0) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    @Override
//    @Cacheable(cacheNames = "cmsComment", key = "#cmsCommentname")
//    public List<CMSComment> getCMSCommentByName(String cmsCommentname) {
//        return cmsCommentRepository.findByCMSCommentName(cmsCommentname);
//    }

    @Override
    @Cacheable(cacheNames = "comment", key = "'cmsComment_list'")
    public List<CMSComment> getAllCMSComment() {
        return cmsCommentRepository.findByDisabledFalse();
    }

}