package net.liuxuan.supportsystem.service.labthink;

import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.spring.helper.bean.BeanHelper;
import net.liuxuan.supportsystem.entity.dto.FAQSearchDTO;
import net.liuxuan.supportsystem.entity.labthink.FAQContent;
import net.liuxuan.supportsystem.entity.security.DbUser;
import net.liuxuan.supportsystem.entity.security.Role;
import net.liuxuan.supportsystem.repository.labthink.FAQContentRepository;
import net.liuxuan.supportsystem.service.CMSCategoryService;
import net.liuxuan.supportsystem.service.util.RoleHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static net.liuxuan.supportsystem.service.util.SearchHelper.*;

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
    private FAQContentRepository faqContentRepository;

    @Autowired
    private CMSCategoryService cmsCategoryService;

    @Autowired
    private RoleHelper roleHelper;

    @Value("${SprKi.faqlist.count}")
    private int listcount;


    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "faqContent", key = "topContents"),
                    @CacheEvict(cacheNames = "faqContent", key = "FaqCount"),
                    @CacheEvict(cacheNames = "faqContent", key = "FaqCountGroup"),
                    @CacheEvict(cacheNames = "faqContent", key = "FaqCountGroupByAuthorAndDate")

            },
            put = {
                    @CachePut(cacheNames = "faqContent", key = "#faq.id", condition = "#{faq.id != null}")
            }
    )
    public FAQContent saveFAQContent(FAQContent faq) {

        FAQContent load = null;

        DbUser u = SystemHelper.getCurrentUser();
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
    @CacheEvict(cacheNames = "faqContent", key = "#id")
    public void refreshCache(Long id) {

    }


    @Cacheable(cacheNames = "faqContent", key = "topContents")
    public List<FAQContent> findTopFAQContents() {
        if (listcount <= 100) {
            return faqContentRepository.findTop100ByDisabledOrderByLastUpdateDateDesc(false);
        } else if (listcount < 200) {
            return faqContentRepository.findTop150ByDisabledOrderByLastUpdateDateDesc(false);
        } else if (listcount < 400) {
            return faqContentRepository.findTop200ByDisabledOrderByLastUpdateDateDesc(false);
        } else {
            return faqContentRepository.findTop400ByDisabledOrderByLastUpdateDateDesc(false);
        }
    }


    @Override
//    @Cacheable(cacheNames = "faqContent", key="#dto",condition="#dto.isAllNull()")
    public List<FAQContent> findAllFAQContentsByDto(FAQSearchDTO dto) {

        if (dto.isAllNull()) {
            return findTopFAQContents();
            //全空，返回top100
//            return faqContentRepository.findTop100ByDisabled(false);

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
    public List<FAQContent> filterListByAccessRight(List<FAQContent> allFAQContents, Set<String> rolenames) {
        List<FAQContent> filteredFAQContents = allFAQContents
                .stream()
                .filter(faq -> hasAccessRight(rolenames, faq))
                .collect(Collectors.toList());
        return filteredFAQContents;
    }


    @Override
    @CacheEvict(cacheNames = "faqContent", key = "#id")
    public void disableFAQContentById(Long id) {
        faqContentRepository.findOne(id).setDisabled(true);
        //faqContentRepository.delete(id);
    }

    @Override
//    @CachePut(cacheNames = "faqContent", key = "#faq.id", condition = "#{faq.id != null}")
    public void revertFAQContentById(Long id) {
        faqContentRepository.findOne(id).setDisabled(false);
        //faqContentRepository.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "faqContent", key = "#id")
    public void deleteFAQContentById(Long id) {
//        faqContentRepository.findOne(id).setDisabled(true);
        faqContentRepository.delete(id);
    }

    @Override
    @Cacheable(cacheNames = "faqContent", key = "#id")
    public FAQContent findById(Long id) {
        FAQContent faq = faqContentRepository.findOne(id);
        if (faq != null) {
            faq.setClicks(faq.getClicks() + 1);
        }
//        faqContentRepository.save(faq);
        return faq;
    }

    @Override
    @Cacheable(cacheNames = "faqContent", key = "FaqCount")
    public long getFAQContentsCount() {
        return faqContentRepository.count();
    }

    @Override
    @Cacheable(cacheNames = "faqContent", key = "FaqCountGroup")
    public List<?> getFaqGroupByCount() {
        return faqContentRepository.findGroupByCount();
    }

    @Override
    @Cacheable(cacheNames = "faqContent", key = "FaqCountGroupByAuthorAndDate")
    public List<Object[]> getFaqGroupByAuthorAndDate() {
        return faqContentRepository.findGroupByAuthorAndDate1();
    }


    /**
     * Judge if the User has role to access the FAQContent.
     *
     * @param rolenames the rolenames
     * @param faq       the faq
     * @return the boolean
     */
    public boolean hasDepartmentRole(Set<String> rolenames, FAQContent faq) {
        return roleHelper.hasDepartmentRole(rolenames, faq.getDepartment());
//        return hasDepartmentRole(rolenames, faq.getDepartment());
    }


    /**
     * Has access right boolean.
     *
     * @param rolenames the rolenames
     * @param faq       the faq
     * @return the boolean
     */
    public boolean hasAccessRight(Set<String> rolenames, FAQContent faq) {
        //rolename 获取一次，节省处理时间
        boolean rtn = roleHelper.isAdmin(rolenames) || roleHelper.isAuthor(faq) || roleHelper.hasDepartmentRole(rolenames, faq.getDepartment());
        return rtn;
    }

    /**
     * Has access right boolean.
     *
     * @param faq the faq
     * @return the boolean
     */
    @Override
    public boolean hasAccessRight(FAQContent faq) {
        List<Role> currentUserRoles = SystemHelper.getCurrentUserRoles();
        Set<String> rolenames = currentUserRoles.stream().map(e -> e.getRolename()).collect(Collectors.toSet());
        return hasAccessRight(rolenames, faq);
    }

}
