package net.liuxuan.SprKi.repository.security;

import java.util.List;
import net.liuxuan.SprKi.entity.security.UrlAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.security.UrlAuthRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/22 14:41
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-22  |    Moses        |     Created
*/

public interface UrlAuthRepository extends JpaRepository<UrlAuth, Long>, JpaSpecificationExecutor<UrlAuth> {
    List<UrlAuth> findByUrlAuthName(String  name);

    List<UrlAuth> findByUrlAuthNameNot(String  NotName);
    List<UrlAuth> findByDisabledFalse();

    List<UrlAuth> findByUrlAuthNameNotOrderByUrlAuthName(String roleNotName);

}


