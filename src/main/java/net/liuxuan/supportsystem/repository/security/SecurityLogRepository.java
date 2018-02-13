package net.liuxuan.supportsystem.repository.security;

import net.liuxuan.supportsystem.entity.security.SecurityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.security.SecurityLogRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2016/04/20 14:52
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2016-04-20  |    Moses        |     Created
*/

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Long>, JpaSpecificationExecutor<SecurityLog> {

//    @Query(value = "select count(v) as cnt, v.devices from SecurityLog v group by v.devices")
    List<SecurityLog> findTop10ByLogLevelOrderByLogTimeDesc(String logLevel);
}


