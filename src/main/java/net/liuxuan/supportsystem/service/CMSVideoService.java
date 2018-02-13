package net.liuxuan.supportsystem.service;


import net.liuxuan.supportsystem.entity.CMSVideo;
import java.util.List;


/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.service.CMSVideoService
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/09/29 13:52
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-09-29  |    Moses        |     Created
*/
public interface CMSVideoService {
    void saveCMSVideo(CMSVideo cMSVideo);

    CMSVideo findCMSVideoById(Long id);

    boolean deleteCMSVideoById(Long id);

    boolean checkCMSVideoExists(String cMSVideoname);

    List<CMSVideo> getCMSVideoByName(String cMSVideoname);

    List<CMSVideo> getAllCMSVideo();

}