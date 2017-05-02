package net.liuxuan.SprKi.service;


import net.liuxuan.SprKi.entity.Message;
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
    void saveMessage(Message message);

    Message findMessageById(Long id);

    boolean deleteMessageById(Long id);

    boolean checkMessageExists(String messagename);

    List<Message> getMessageByName(String messagename);

    List<Message> getAllMessage();

}