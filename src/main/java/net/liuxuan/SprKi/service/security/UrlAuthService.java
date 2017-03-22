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
    void saveUrlAuth(UrlAuth urlAuth);

    UrlAuth findUrlAuthById(Long id);

    boolean deleteUrlAuthById(Long id);

    boolean checkUrlAuthExists(String urlAuthname);

    List<UrlAuth> getAllUrlAuth();

}