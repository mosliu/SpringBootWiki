package net.liuxuan.SprKi.service.faq;


import net.liuxuan.SprKi.entity.DTO.FAQSearchDTO;
import net.liuxuan.SprKi.entity.labthink.FAQContent;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.faq.FAQContentService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 15:22
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/16  |    Moses       |     Created
 */
public interface FAQContentService {
    void saveFAQContent(FAQContent faq);
    public List<FAQContent> findAllFAQContentsByDto(FAQSearchDTO dto);
    public void deleteFAQContentById(Long id);
    public FAQContent findById(Long id);
}
