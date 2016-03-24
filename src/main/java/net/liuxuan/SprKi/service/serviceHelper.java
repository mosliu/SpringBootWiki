package net.liuxuan.SprKi.service;

import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.repository.security.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.ServiceHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/24 9:29
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/24  |    Moses       |     Created
 */
public class ServiceHelper {


    @Autowired
    private UsersRepository usersRepository;

    public static Users getCurrentUsers() {
        User ui = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Users u = new Users();
        u.setUsername(ui.getUsername());
        return u;
    }
    public Users getCurrentUsersFromDb() {
        User ui = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Users u = usersRepository.getOne(ui.getUsername());
        return u;
    }
}
