package net.liuxuan.SprKi.service.faq;

import net.liuxuan.SprKi.entity.DTO.FAQSearchDTO;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.repository.labthink.FAQContentRepository;
import net.liuxuan.SprKi.service.ServiceHelper;
import net.liuxuan.spring.Helper.bean.BeanHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

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

    private static Logger log = LoggerFactory.getLogger(FAQContentServiceImpl.class);

    @Autowired
    FAQContentRepository faqContentRepository;


    @Override
    public void saveFAQContent(FAQContent faq) {

        FAQContent load = null;

        Users u = ServiceHelper.getCurrentUsers();
        faq.setLastUpdateUser(u);
        Date now = new Date();

        if (faq.getId() == null) {
            //新的
            log.trace("===saveFAQContent logged ,faq is null", faq);
            faq.setAuthor(u);

            faq.setLastUpdateDate(now);
            faq.setPublishDate(now);
        } else {
            load = faqContentRepository.getOne(faq.getId());
            try {
                log.trace("===saveFAQContent logged ,the value Before COPY is : {}", faq);
                BeanHelper.copyWhenDestFiledNull(faq, load);
                log.trace("===saveFAQContent logged ,the value After  COPY is : {}", faq);
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
    public List<FAQContent> findAllFAQContentsByDto(FAQSearchDTO dto) {

        if (dto.isAllNull()) {
            //全空，返回top100
            return faqContentRepository.findTop100ByDisabled(false);
        }

        return faqContentRepository.findAll(new Specification<FAQContent>() {
            @Override
            public Predicate toPredicate(Root<FAQContent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                root = query.from(FAQContent.class);

//
//
//                try {
//                    Class<? extends FAQSearchDTO> dtoClass = dto.getClass();
//                    Field[] dtoClassFields = dtoClass.getFields();
//
//                    for (Field field : dtoClassFields) {
//                        String name = field.getName();
//                        if(!ArrayUtils.contains(sl,name)){
//                            continue;
//                        }
//                        log.debug("checking filed:{}", name);
//                        //判断属性是否为空
//                        if (field.get(dto) == null) {
//                            //跳过
//                        } else {
//                            Class<?> type = field.getType();
//                            Constructor<?>[] constructors = type.getConstructors();
//                            Object o = constructors[0].newInstance();
//                            Path p = root.get(name);
//                            Predicate predicateCategory = cb.equal(p, field.get(dto));
//                            log.debug("add filed:{}", name);
//                            pl.add(cb.and(predicateCategory));
//                        }
//
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//


//
//
//                if(dto.category!=null) {
//                    log.info("category not null");
//                    Path<CMSCategory> categoryPath = root.get("category");
//
//                    Predicate predicateCategory = cb.equal(categoryPath, dto.category);
////                    pl.add(cb.and(predicateCategory));
//                    pl.add(predicateCategory);
//                }
//
//                if(dto.devices!=null) {
//                    log.info("devices not null");
//                    Path<CMSCategory> devicesPath = root.get("devices");
//                    Predicate predicatedevices = cb.equal(devicesPath, dto.devices);
//                    pl.add(predicatedevices);
////                    pl.add(cb.and(predicatedevices));
//                }


//
//                query.where(predicateCategory);
//                Path<String> namePath = root.get("name");
//                Path<String> nicknamePath = root.get("nickname");
//                /**
//                 * 连接查询条件, 不定参数，可以连接0..N个查询条件
//                 */
//                Predicate like = cb.like(namePath, "%李%");
//
//                pl.add(like);


                //形成条件列表
                List<Predicate> pl = new ArrayList<Predicate>();

                String[] sl_and = {"category", "devices", "department", "disabled"};

                //先将Object 转为Map，这样好向内添加属性
                Map<String, Object> objectMap = object2Map(dto);
                objectMap.put("disabled", false);

                pl.addAll(buildEqualsAndPredicateBuilder(root, cb, sl_and, objectMap));

                Map<String, Object> orMap = new LinkedHashMap<String, Object>();
                String[] sl_or = {"author", "lastUpdateUser"};

                if (StringUtils.isNotBlank(dto.user)) {
                    Users users = new Users();
                    users.setUsername(dto.user);
                    orMap.put("author", users);
                    orMap.put("lastUpdateUser", users);
                }

                pl.addAll(convertToOrPredict(buildEqualsAndPredicateBuilder(root, cb, sl_or, orMap),cb));

                String[] likepaths ={"title","question","answer"};
                String keyword = dto.keyword;

                pl.addAll(convertToOrPredict(buildStringAndLikePredict(root, cb, likepaths, keyword),cb));


                log.debug("pl size is:{}", pl.size());
                query.where(pl.toArray(new Predicate[pl.size()]));
//                query.where(cb.like(namePath, "%李%"), cb.like(nicknamePath, "%王%")); //这里可以设置任意条查询条件
//                Path<String> nameExp = root.get("name");
                return null;
            }
        });

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
        faq.setClicks(faq.getClicks() + 1);
//        faqContentRepository.save(faq);
        return faq;
    }


}
