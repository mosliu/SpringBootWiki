package net.liuxuan.supportsystem.repository.labthink;

import net.liuxuan.supportsystem.entity.labthink.FAQContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    List<FAQContent> findTop100ByDisabled(Boolean disabled);
    List<FAQContent>  findTop100ByDisabledOrderByLastUpdateDateDesc(Boolean disabled);
    List<FAQContent>  findTop150ByDisabledOrderByLastUpdateDateDesc(Boolean disabled);
    List<FAQContent>  findTop200ByDisabledOrderByLastUpdateDateDesc(Boolean disabled);
    List<FAQContent>  findTop400ByDisabledOrderByLastUpdateDateDesc(Boolean disabled);


    @Query(value = "select count(v) as cnt, v.devices from FAQContent v group by v.devices")
    List<?> findGroupByCount();
//    @Query(value = "select count(*),EXTRACT(YEAR_MONTH FROM last_update_date) date,author FROM sprki_cms_content GROUP BY date,author", nativeQuery = true)
    @Query(value = "select count(*),EXTRACT(YEAR_MONTH FROM last_update_date) date,author FROM sprki_cms_content ,sprki_cms_contentfaq WHERE sprki_cms_contentfaq.faq_id = sprki_cms_content.id GROUP BY date,author", nativeQuery = true)
    List<Object[]> findGroupByAuthorAndDate();

//    @Query(value = "select count(author),SUBSTRING(faq.lastUpdateDate, 0, 7) ,author FROM FAQContent faq GROUP BY SUBSTRING(faq.lastUpdateDate, 0, 7) ,author")
    @Query(value = "select count(author),CONCAT(YEAR(faq.lastUpdateDate),MONTH(faq.lastUpdateDate)) ,author FROM FAQContent faq GROUP BY CONCAT(YEAR(faq.lastUpdateDate),MONTH(faq.lastUpdateDate)) ,author")
    List<Object[]> findGroupByAuthorAndDate1();
}
