package net.liuxuan.SprKi.service.security;


import net.liuxuan.SprKi.entity.security.UrlAuth;
import java.util.List;


/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.security.UrlAuthService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/03/22 14:41
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-03-22  |    Moses        |     Created
 */
public interface UrlAuthService {
    /**
     * Save url auth.
     *
     * @param urlAuth the url auth
     */
    void saveUrlAuth(UrlAuth urlAuth);

    /**
     * Find url auth by id url auth.
     *
     * @param id the id
     * @return the url auth
     */
    UrlAuth findUrlAuthById(Long id);

    /**
     * Delete url auth by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteUrlAuthById(Long id);

    /**
     * Check url auth exists boolean.
     *
     * @param urlAuthname the url authname
     * @return the boolean
     */
    boolean checkUrlAuthExists(String urlAuthname);

    /**
     * Gets all url auth.
     *
     * @return the all url auth
     */
    List<UrlAuth> getAllUrlAuth();

    /**
     * Find by url auth name list.
     *
     * @param urlAuthname the url authname
     * @return the list
     */
    List<UrlAuth> findByUrlAuthName(String urlAuthname);

}