package net.liuxuan.supportsystem.service.labthink;


import net.liuxuan.supportsystem.entity.DTO.TicketSearchDTO;
import net.liuxuan.supportsystem.entity.labthink.TicketContent;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.labthink.FAQContentService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 15:22
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/8/24 |    Moses       |     Created
 */
public interface TicketContentService {
    /**
     * Save ticket content.
     *
     * @param ticket the ticket
     */
    void saveTicketContent(TicketContent ticket);

    /**
     * Find all ticket contents by dto list.
     *
     * @param dto the dto
     * @return the list
     */
    List<TicketContent> findAllTicketContentsByDto(TicketSearchDTO dto);

    List<TicketContent> findAllTicketContentsAssignedTo(UserDetailInfo assignToUser);

    List<TicketContent> findAllTicketContentsAssignedTo(UserDetailInfo assignToUser, boolean isResolved);

    Long getCountByAssignAndResolved(UserDetailInfo assignToUser, boolean isResolved);

    /**
     * Delete ticket content by id.
     *
     * @param id the id
     */
    void deleteTicketContentById(Long id);

    /**
     * Find by id faq content.
     *
     * @param id the id
     * @return the ticket content
     */
    TicketContent findById(Long id);

    /**
     * Gets ticket contents count.
     *
     * @return the ticket contents count
     */
    long getTicketContentsCount();
}
