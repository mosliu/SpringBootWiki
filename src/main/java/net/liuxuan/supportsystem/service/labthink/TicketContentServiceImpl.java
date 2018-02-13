package net.liuxuan.supportsystem.service.labthink;

import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.spring.helper.bean.BeanHelper;
import net.liuxuan.supportsystem.entity.dto.TicketSearchDTO;
import net.liuxuan.supportsystem.entity.labthink.TicketContent;
import net.liuxuan.supportsystem.entity.security.DbUser;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import net.liuxuan.supportsystem.repository.labthink.TicketContentRepository;
import net.liuxuan.supportsystem.service.CMSCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

import static net.liuxuan.supportsystem.service.util.SearchHelper.*;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.ticket.TicketServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 16:30
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/8/24  |    Moses       |     Created
 */
@Service
@Transactional
public class TicketContentServiceImpl implements TicketContentService {

    private static Logger log = LoggerFactory.getLogger(TicketContentServiceImpl.class);

    public  static final  String TICKETCATEGORY = TicketContent.class.getSimpleName();
    @Autowired
    private CMSCategoryService cmsCategoryService;

    @Autowired
    private TicketContentRepository ticketContentRepository;


    @Override
//    @CachePut(cacheNames = "ticketContent", key = "#faq.id", condition = "#{faq.id != null}")
    public void saveTicketContent(TicketContent ticket) {

        TicketContent load = null;

        DbUser u = SystemHelper.getCurrentUser();
        ticket.setLastUpdateUser(u);
        Date now = new Date();

        if (ticket.getId() == null) {
            //新的
            log.trace("===saveTicketContent logged ,ticket is null", ticket);
            ticket.setAuthor(u);
            ticket.setLastUpdateDate(now);
            ticket.setCategory(cmsCategoryService.getOrCreateOneByName(TICKETCATEGORY));
            ticket.setPublishDate(now);
        } else {
            load = ticketContentRepository.getOne(ticket.getId());
            try {
                log.trace("===saveTicketContent logged ,the value Before COPY is : {}", ticket);
                BeanHelper.copyWhenDestFieldNull(ticket, load);
                log.trace("===saveTicketContent logged ,the value After  COPY is : {}", ticket);
            } catch (InvocationTargetException e) {
                log.error("Copy ticket error!",e);
//                e.printStackTrace();
            } catch (IllegalAccessException e) {
                log.error("Copy ticket error!",e);
//                e.printStackTrace();
            }
        }

        ticket.setLastUpdateDate(now);
        ticketContentRepository.save(ticket);

    }

    @Override
    @Cacheable(cacheNames = "ticketContent", key="#dto")
    public List<TicketContent> findAllTicketContentsByDto(TicketSearchDTO dto) {

        if (dto.isAllNull()) {
            //全空，返回top100
            return ticketContentRepository.findTop100ByDisabledOrderByLastUpdateDateDesc(false);
        }

        return ticketContentRepository.findAll(new Specification<TicketContent>() {
            @Override
            public Predicate toPredicate(Root<TicketContent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                //形成条件列表
                List<Predicate> pl = new ArrayList<Predicate>();

                //按lastUpdateDate比较时间条件
                List<Predicate> pl_datecompare = buildDateComparePredicates(root, cb, "lastUpdateDate", dto.fromDate, dto.toDate);

                pl.addAll(pl_datecompare);

                String[] sl_and = {"category", "devices", "department", "disabled"};

                //先将Object 转为Map，这样好向内添加属性
                Map<String, Object> objectMap = object2Map(dto);
                objectMap.put("disabled", false);


                pl.addAll(buildEqualsAndPredicate(root, cb, sl_and, objectMap));

                //比较用户名项的比较列
                String[] sl_or = {"author", "lastUpdateUser"};
                List<Predicate> pl_usercompare = buildUserEqualsPredicates(root, cb, sl_or, dto.user);
                pl.addAll(pl_usercompare);

                //TODO 这里有问题
                String[] likepaths = {"title", "question"};
                pl.addAll(convertToOrPredict(buildStringAndLikePredict(root, cb, likepaths, dto.keyword), cb));


                log.debug("pl size is:{}", pl.size());
                query.where(pl.toArray(new Predicate[pl.size()])).orderBy(cb.desc(root.get("lastUpdateDate")));
//                query.where(cb.like(namePath, "%李%"), cb.like(nicknamePath, "%王%")); //这里可以设置任意条查询条件
//                Path<String> nameExp = root.get("name");
                return null;
            }
        });

//        return ticketContentRepository.findAll();
    }

    @Override
    public List<TicketContent> findAllTicketContentsAssignedTo(UserDetailInfo assignToUser){
        List<TicketContent> ticketContents = ticketContentRepository.findAllByDisabledFalseAndAssignToUserOrderByLastUpdateDateDesc(assignToUser);
        return ticketContents;
    }

    @Override
    public List<TicketContent> findAllTicketContentsAssignedTo(UserDetailInfo assignToUser, boolean isResolved){
        List<TicketContent> ticketContents =
                ticketContentRepository.findAllByDisabledFalseAndAssignToUserAndResolvedOrderByLastUpdateDateDesc(assignToUser,isResolved);
        return ticketContents;
    }

    @Override
    public  Long getCountByAssignAndResolved(UserDetailInfo assignToUser, boolean isResolved){
        return ticketContentRepository.countByDisabledFalseAndAssignToUserAndResolvedOrderByLastUpdateDateDesc(assignToUser,isResolved);
    }

    @Override
    @CacheEvict(cacheNames = "ticketContent",key="#id")
    public void deleteTicketContentById(Long id) {
        ticketContentRepository.findOne(id).setDisabled(true);
        //ticketContentRepository.delete(id);
    }

    @Override
    @Cacheable(cacheNames = "ticketContent", key="#id")
    public TicketContent findById(Long id) {
        TicketContent ticket = ticketContentRepository.findOne(id);
        ticket.setClicks(ticket.getClicks() + 1);
//        ticketContentRepository.save(ticket);
        return ticket;
    }



    @Override
    public long getTicketContentsCount() {
        return ticketContentRepository.count();
    }




}
