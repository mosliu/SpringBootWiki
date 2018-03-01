package net.liuxuan.supportsystem.service;

import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.supportsystem.entity.Message;
import net.liuxuan.supportsystem.entity.MessageConst;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import net.liuxuan.supportsystem.repository.MessageRepository;
import net.liuxuan.supportsystem.service.labthink.TicketContentService;
import net.liuxuan.supportsystem.service.user.UserDetailInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.MessageServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/04/22 09:48
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-04-22  |    Moses        |     Created
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserDetailInfoService userDetailInfoService;

    @Autowired
    private TicketContentService ticketContentService;

    @Override
//    @Cacheable(cacheNames = "message", key = "#message.id")
    @Caching(
            evict = {
//                    @CacheEvict(cacheNames = "message", key = "#message.id"),
//                    @CacheEvict(cacheNames = "userMessage",key = "'SMSFROM'+#message.fromUser.dbUser.username"),
//                    @CacheEvict(cacheNames = "userMessage",key = "'SMSTO'+#message.toUser.dbUser.username"),
                    @CacheEvict(cacheNames = "userMessage", key = "'COUNT'+#message.toUser.dbUser.username")
            },
            put = {
                    @CachePut(cacheNames = "message", key = "#message.id")
            }
    )
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void sentMessage(Message message) {
        message.setSendTime(new Date());
        saveMessage(message);
    }

    @Override
    public void announceMessage(String title, String content){
        UserDetailInfo adminUser = userDetailInfoService.findUserDetailInfoByUsername("admin");
        List<UserDetailInfo> userDetailInfos = userDetailInfoService.listAllUserDetailInfos();
        Date sendTime = new Date();
        userDetailInfos.forEach((UserDetailInfo e) ->{
            Message message = new Message();
            message.setTitle(title);
            message.setContent(content);
            message.setFromUser(adminUser);
            message.setToUser(e);
            message.setSendTime(sendTime);
            message.setMessageType(MessageConst.MSG_TYPE_SYSTEMANNOUNCE);
            message.setStatus(MessageConst.MSG_STATUS_SENT);
            saveMessage(message);
//            messageRepository.save(message);
        });



    }

    @Override
    public void markReadMessage(Message message) {
        message.setStatus(MessageConst.MSG_STATUS_READ);
        saveMessage(message);
    }


    @Override
    @Cacheable(cacheNames = "message", key = "#id")
    public Message findMessageById(Long id) {
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
//        Pageable pageable = new PageRequest(1, 15, sort);
//        Page<Message> all = messageRepository.findAll(pageable);
        Message message = messageRepository.findOne(id);
        return message;
    }

    @Override
//    @CacheEvict(cacheNames = "message", key = "'SMSTO'+ServiceHelper.getCurrentUser().username")
    @CacheEvict(cacheNames = "message", key = "#id")
    public boolean deleteMessageById(Long id) {

        Message message = messageRepository.getOne(id);
        if (message != null) {
            message.setDeleted(true);
            return true;
        }
        return false;
    }


    @Override
    @Cacheable(cacheNames = "message", key = "'message_list'")
    public List<Message> getAllMessage() {
        return messageRepository.findByDeletedFalse();
    }

    @Override
//    @Cacheable(cacheNames = "userMessage", key = "'SMSTO'+#currentUserDetailInfo.dbUser.username")
    public List<Message> findMessageToUser(UserDetailInfo currentUserDetailInfo) {
        return messageRepository.findByDeletedFalseAndToUserIsOrderBySendTimeDesc(currentUserDetailInfo);
    }
    @Override
    public Page<Message> findMessageToUserPageable(Integer page, Integer size,UserDetailInfo currentUserDetailInfo) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "SendTime");
        return messageRepository.findByDeletedFalseAndToUserIsOrderBySendTimeDesc(currentUserDetailInfo,pageable);
    }

//    @Cacheable(cacheNames = "userMessage", key = "'SMSFROM'+#currentUserDetailInfo.dbUser.username")
    @Override
    public List<Message> findMessageFromUser(UserDetailInfo currentUserDetailInfo) {
        return messageRepository.findByDeletedFalseAndFromUserIs(currentUserDetailInfo);
    }

    @Override
    public List<Message> deleteMessageByIds(Collection<Long> ids) {
        List<Message> byDeletedFalseAndIdIn = messageRepository.findByDeletedFalseAndIdIn(ids);
        UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();
        byDeletedFalseAndIdIn.forEach(e->{
            if(e.getToUser().getId()==currentUserDetailInfo.getId()){
                e.setDeleted(true);
            }
        });
        return null;
    }

    @Override
//    @Cacheable(cacheNames = "userMessage", key = "'COUNT'+#currentUserDetailInfo.dbUser.username")
    @Cacheable(cacheNames = "message", key = "'COUNT'+#currentUserDetailInfo.dbUser.username")
    public Long getUnreadMessageCount(UserDetailInfo currentUserDetailInfo) {
        Long countByStatusAndToUser = messageRepository.countByStatusAndToUserIs(MessageConst.MSG_STATUS_SENT, currentUserDetailInfo);
        return countByStatusAndToUser;
    }

    @Override
    public Long getUnreadMessageCount() {
        Long countByStatusAndToUser = getUnreadMessageCount(SystemHelper.getCurrentUserDetailInfo());
        return countByStatusAndToUser;
//        return 0L;
    }

    @Override
    public Long getUnreadMessageAndUnResolvedTicketCount(UserDetailInfo currentUserDetailInfo) {
        Long count = ticketContentService.getCountByAssignAndResolved(currentUserDetailInfo,false);
        count +=getUnreadMessageCount(currentUserDetailInfo);
        return count;
    }
    @Override
    public Long getUnreadMessageAndUnResolvedTicketCount() {
        Long count = getUnreadMessageAndUnResolvedTicketCount(SystemHelper.getCurrentUserDetailInfo());

        return count;
//        return 0L;
    }

}