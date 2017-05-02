package net.liuxuan.SprKi.repository;

import java.util.List;
import net.liuxuan.SprKi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.MessageRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/04/22 09:48
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-04-22  |    Moses        |     Created
*/

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    List<Message> findByMessageName(String  name);

    List<Message> findByMessageNameNot(String  NotName);
    List<Message> findByDisabledFalse();

    List<Message> findByMessageNameNotOrderByMessageName(String roleNotName);

}


