package net.liuxuan.SprKi.service;


import net.liuxuan.SprKi.entity.CMSComment;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.CMSCommentService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/06/21 14:36
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-06-21  |    Moses        |     Created
*/
public interface CMSCommentService {
    CMSComment saveCMSComment(CMSComment cMSComment);

    CMSComment findCMSCommentById(Long id);

    boolean deleteCMSCommentById(Long id);

//    boolean checkCMSCommentExists(String cMSCommentname);

//    List<CMSComment> getCMSCommentByName(String cMSCommentname);

    List<CMSComment> getAllCMSComment();

}