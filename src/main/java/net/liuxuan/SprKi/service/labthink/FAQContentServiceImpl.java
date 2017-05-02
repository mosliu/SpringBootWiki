package net.liuxuan.SprKi.service.labthink;

import net.liuxuan.SprKi.entity.DTO.FAQSearchDTO;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.repository.labthink.FAQContentRepository;
import net.liuxuan.SprKi.service.CMSCategoryService;
import net.liuxuan.SprKi.service.ServiceHelper;
import net.liuxuan.spring.Helper.bean.BeanHelper;
import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static net.liuxuan.SprKi.service.util.SearchHelper.*;

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

    public static final String FAQCATEGORY = FAQContent.class.getSimpleName();
    private static Logger log = LoggerFactory.getLogger(FAQContentServiceImpl.class);
    @Autowired
    FAQContentRepository faqContentRepository;

    @Autowired
    CMSCategoryService cmsCategoryService;


    @Override
    @CachePut(cacheNames = "faqContent", key = "#faq.id", condition = "#{faq.id != null}")
    public FAQContent saveFAQContent(FAQContent faq) {

        FAQContent load = null;

        DbUser u = ServiceHelper.getCurrentUsers();
        faq.setLastUpdateUser(u);
        Date now = new Date();

        if (faq.getId() == null) {
            //新的
            log.trace("===saveFAQContent:new", faq);
            faq.setAuthor(u);

            faq.setCategory(cmsCategoryService.getOrCreateOneByName(FAQCATEGORY));
            faq.setPublishDate(now);
        } else if (!faqContentRepository.exists(faq.getId())) {
            //新的
            log.trace("===saveFAQContent:new", faq);
            faq.setAuthor(u);
            faq.setCategory(cmsCategoryService.getOrCreateOneByName(FAQCATEGORY));
            faq.setPublishDate(now);
        } else {
            load = faqContentRepository.getOne(faq.getId());
            try {
                log.trace("===saveFAQContent logged ,the value Before COPY is : {}", faq);
                BeanHelper.copyWhenDestFieldNull(faq, load);
                log.trace("===saveFAQContent logged ,the value After  COPY is : {}", faq);
            } catch (InvocationTargetException e) {
                log.error("Copy faq error!", e);
//                e.printStackTrace();
            } catch (IllegalAccessException e) {
                log.error("Copy faq error!", e);
//                e.printStackTrace();
            }

        }

        faq.setLastUpdateDate(now);
        return faqContentRepository.save(faq);

    }


    @Override
    @Cacheable(cacheNames = "faqContent", key="#dto")
    public List<FAQContent> findAllFAQContentsByDto(FAQSearchDTO dto) {

        if (dto.isAllNull()) {
            //全空，返回top100
//            return faqContentRepository.findTop100ByDisabled(false);
            return faqContentRepository.findTop100ByDisabledOrderByLastUpdateDateDesc(false);
        }


        return faqContentRepository.findAll(new Specification<FAQContent>() {
            @Override
            public Predicate toPredicate(Root<FAQContent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //形成条件列表
                List<Predicate> pl = new ArrayList<Predicate>();

                //按lastUpdateDate比较时间条件
                List<Predicate> pl_datecompare = buildDateComparePredicates(root, cb, "lastUpdateDate", dto.fromDate, dto.toDate);

                pl.addAll(pl_datecompare);


                String[] sl_and = {"deviceType", "category", "devices", "department", "disabled"};

                //先将Object 转为Map，这样好向内添加属性
                Map<String, Object> objectMap = object2Map(dto);
//                objectMap.put("disabled", false);
                pl.addAll(buildEqualsAndPredicate(root, cb, sl_and, objectMap));

                //比较用户名项的比较列
                String[] sl_or = {"author", "lastUpdateUser"};
                List<Predicate> pl_usercompare = buildUserEqualsPredicates(root, cb, sl_or, dto.user);
                pl.addAll(pl_usercompare);

                String[] likepaths = {"title", "question", "answer", "standard"};
                String keyword = dto.keyword;
                pl.addAll(convertToOrPredict(buildStringAndLikePredict(root, cb, likepaths, keyword), cb));

                String[] standardlikepaths = {"standard"};
                String standard = dto.standard;
                pl.addAll(convertToOrPredict(buildStringAndLikePredict(root, cb, standardlikepaths, standard), cb));

//                log.debug("pl size is:{}", pl.size());
                query
                        .where(pl.toArray(new Predicate[pl.size()]))
                        .orderBy(cb.desc(root.get("lastUpdateDate")));
//                query.where(cb.like(namePath, "%李%"), cb.like(nicknamePath, "%王%")); //这里可以设置任意条查询条件
//                Path<String> nameExp = root.get("name");
                return null;
            }
        });

//        return faqContentRepository.findAll();
    }


    @Override
    @CacheEvict(cacheNames = "faqContent",key="#id")
    public void disableFAQContentById(Long id) {
        faqContentRepository.findOne(id).setDisabled(true);
        //faqContentRepository.delete(id);
    }

    @Override
    @CachePut(cacheNames = "faqContent", key = "#faq.id", condition = "#{faq.id != null}")
    public void revertFAQContentById(Long id) {
        faqContentRepository.findOne(id).setDisabled(false);
        //faqContentRepository.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "faqContent",key="#id")
    public void deleteFAQContentById(Long id) {
//        faqContentRepository.findOne(id).setDisabled(true);
        faqContentRepository.delete(id);
    }

    @Override
    @Cacheable(cacheNames = "faqContent", key="#id")
    public FAQContent findById(Long id) {
        FAQContent faq = faqContentRepository.findOne(id);
        if (faq != null) {
            faq.setClicks(faq.getClicks() + 1);
        }
//        faqContentRepository.save(faq);
        return faq;
    }

    @Override
    public long getFAQContentsCount() {
        return faqContentRepository.count();
    }

    @Override
    public List<?> getFaqGroupByCount() {
        return faqContentRepository.findGroupByCount();
    }

    @Override
    public List<Object[]> getFaqGroupByAuthorAndDate() {
        return faqContentRepository.findGroupByAuthorAndDate();
    }


}
