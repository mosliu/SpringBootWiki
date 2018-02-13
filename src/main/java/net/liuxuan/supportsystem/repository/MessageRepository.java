package net.liuxuan.supportsystem.repository;

import java.util.Collection;
import java.util.List;
import net.liuxuan.supportsystem.entity.Message;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    List<Message> findByDeletedFalse();

    List<Message> findByDeletedFalseAndFromUserIs(UserDetailInfo user);
    List<Message> findByDeletedFalseAndToUserIs(UserDetailInfo user);
    List<Message> findByDeletedFalseAndToUserIsOrderBySendTimeDesc(UserDetailInfo user);

    List<Message> findByDeletedFalseAndIdIn(Collection<Long> ids);

    Page<Message> findByDeletedFalseAndToUserIsOrderBySendTimeDesc(UserDetailInfo user,Pageable pageable);


    Long countByStatusAndToUserIs(String status,UserDetailInfo user);

}


