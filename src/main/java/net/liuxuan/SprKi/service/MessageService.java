package net.liuxuan.SprKi.service;


import net.liuxuan.SprKi.entity.Message;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.MessageService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/04/22 09:48
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-04-22  |    Moses        |     Created
*/
public interface MessageService {
    Message saveMessage(Message message);
    void sentMessage(Message message);

    void announceMessage(String title, String content);

    void markReadMessage(Message message);

    Message findMessageById(Long id);

    boolean deleteMessageById(Long id);

    List<Message> getAllMessage();

    List<Message> findMessageToUser(UserDetailInfo currentUserDetailInfo);


    Page<Message> findMessageToUserPageable(Integer page, Integer size, UserDetailInfo currentUserDetailInfo);

    List<Message> findMessageFromUser(UserDetailInfo currentUserDetailInfo);

    List<Message> deleteMessageByIds(Collection<Long> ids);

    Long getUnreadMessageCount(UserDetailInfo currentUserDetailInfo);
    Long getUnreadMessageCount();
}