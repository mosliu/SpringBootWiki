package net.liuxuan.spring.mvc;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.GlobalInit
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/22 11:39
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/22  |    Moses       |     Created
 */
public class GlobalInit {
    public void doInit(){
        //初始化remeberme
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setCreateTableOnStartup(true);
    }
}
