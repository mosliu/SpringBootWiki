package net.liuxuan.SprKi.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import net.liuxuan.SprKi.repository.MessageRepository;
import net.liuxuan.SprKi.entity.Message;
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
public class MessageServiceImpl implements MessageService{

    private static Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    MessageRepository messageRepository;

    @Override
    @Cacheable(cacheNames = "message", key = "#message.id")
    public void saveMessage(Message message){
        messageRepository.save(message);
    }

    @Override
    @Cacheable(cacheNames = "message", key = "#id")
    public Message findMessageById(Long id){
        Message message = messageRepository.findOne(id);
        return message;
    }

    @Override
    @CacheEvict(cacheNames = "message", key = "#id")
    public boolean deleteMessageById(Long id){
        Message message = messageRepository.getOne(id);
        if (message != null) {
            message.setDisabled(true);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkMessageExists(String messagename){
        List<Message> list = getMessageByName(messagename);
        if (list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Cacheable(cacheNames = "message", key = "#messagename")
    public List<Message> getMessageByName(String messagename) {
        return messageRepository.findByMessageName(messagename);
    }

    @Override
    @Cacheable(cacheNames = "message", key = "'message_list'")
    public List<Message> getAllMessage() {
        return messageRepository.findByDisabledFalse();
    }

}