package net.liuxuan.SprKi.service.faq;

import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.repository.labthink.FAQContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    FAQContentRepository faqContentRepository;

    @Override
    public void saveFAQContent(FAQContent faqContent) {
        //Column 'author' cannot be null
        //Column 'last_update_user' cannot be null
        faqContentRepository.save(faqContent);
    }

    @Override
    public List<FAQContent> findAllFAQContents() {
        return faqContentRepository.findAll();
    }

    @Override
    public void deleteFAQContentById(Long id) {
        faqContentRepository.delete(id);
    }

    @Override
    public FAQContent findById(Long id) {
        return faqContentRepository.findOne(id);
    }
}
