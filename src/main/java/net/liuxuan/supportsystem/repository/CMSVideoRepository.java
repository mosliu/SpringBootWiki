package net.liuxuan.supportsystem.repository;

import java.util.List;
import net.liuxuan.supportsystem.entity.CMSVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.CMSVideoRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/09/29 13:52
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-09-29  |    Moses        |     Created
*/

public interface CMSVideoRepository extends JpaRepository<CMSVideo, Long>, JpaSpecificationExecutor<CMSVideo> {
    List<CMSVideo> findByCmsVideoName(String  name);

    List<CMSVideo> findByCmsVideoNameNot(String  NotName);

    List<CMSVideo> findByCmsVideoNameNotOrderByCmsVideoName(String roleNotName);

}


