package net.liuxuan.SprKi.service.faq;

import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.repository.labthink.FAQContentRepository;
import net.liuxuan.SprKi.service.ServiceHelper;
import net.liuxuan.spring.Helper.BeanHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.faq.FAQServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 16:30
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/16  |    Moses       |     Created
 */
@Service
@Transactional
public class FAQContentServiceImpl implements FAQContentService {

    private static Logger log =  LoggerFactory.getLogger(FAQContentServiceImpl.class);

    @Autowired
    FAQContentRepository faqContentRepository;

    @Override
    public void saveFAQContent(FAQContent faq) {

        FAQContent load =null;

        Users u = ServiceHelper.getCurrentUsers();
        faq.setLastUpdateUser(u);
        Date now = new Date();

        if (faq.getId() == null) {
            //新的
            log.trace("===saveFAQContent logged ,faq is null",faq);
            faq.setAuthor(u);

            faq.setLastUpdateDate(now);
            faq.setPublishDate(now);
        }else{
            load = faqContentRepository.getOne(faq.getId());
            try {
                log.trace("===saveFAQContent logged ,the value Before COPY is : {}",faq);
                BeanHelper.copyWhenNull(faq,load);
                log.trace("===saveFAQContent logged ,the value After  COPY is : {}",faq);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        faq.setLastUpdateDate(now);
        faqContentRepository.save(faq);

    }



    @Override
    public List<FAQContent> findAllFAQContents() {
        return faqContentRepository.findTop100ByDisabled(false);
//        return faqContentRepository.findAll();
    }

    @Override
    public void deleteFAQContentById(Long id) {
        faqContentRepository.findOne(id).setDisabled(true);
        //faqContentRepository.delete(id);
    }

    @Override
    public FAQContent findById(Long id) {
        FAQContent faq = faqContentRepository.findOne(id);
        faq.setClicks(faq.getClicks()+1);
//        faqContentRepository.save(faq);
        return faq;
    }
}
