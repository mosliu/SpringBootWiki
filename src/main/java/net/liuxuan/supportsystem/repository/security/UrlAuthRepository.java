package net.liuxuan.supportsystem.repository.security;

import net.liuxuan.supportsystem.entity.security.UrlAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

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
    /**
     * Find by url auth name list.
     *
     * @param name the name
     * @return the list
     */
    List<UrlAuth> findByUrlAuthName(String  name);

    /**
     * Find by url auth name not list.
     *
     * @param NotName the not name
     * @return the list
     */
    List<UrlAuth> findByUrlAuthNameNot(String  NotName);

    /**
     * Find by disabled false list.
     *
     * @return the list
     */
    List<UrlAuth> findByDisabledFalse();

    /**
     * Find by url auth name not order by url auth name list.
     *
     * @param roleNotName the role not name
     * @return the list
     */
    List<UrlAuth> findByUrlAuthNameNotOrderByUrlAuthName(String roleNotName);

}


