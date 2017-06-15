package net.liuxuan.SprKi.repository.labthink;

import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.entity.labthink.TicketContent;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.repository.labthink.FAQContentRepository
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 16:24
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/8/24  |    Moses       |     Created
 */
public interface TicketContentRepository extends JpaRepository<TicketContent, Long>, JpaSpecificationExecutor<TicketContent> {

    List<TicketContent> findTop100ByDisabledOrderByLastUpdateDateDesc(Boolean disabled);

    List<TicketContent> findAllByDisabledFalseAndAssignToUserOrderByLastUpdateDateDesc(UserDetailInfo assignToUser);

    List<TicketContent> findAllByDisabledFalseAndAssignToUserAndResolvedOrderByLastUpdateDateDesc(UserDetailInfo assignToUser,Boolean resolved);

    Long countByDisabledFalseAndAssignToUserAndResolvedOrderByLastUpdateDateDesc(UserDetailInfo assignToUser,Boolean resolved);


}
