package net.liuxuan.SprKi.service;

import net.liuxuan.SprKi.entity.security.DbUser;
import net.liuxuan.SprKi.repository.security.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
@Component
public class ServiceHelper {


    public static UsersRepository usersRepo;

    @Autowired
    private UsersRepository usersRepository;


    @PostConstruct
    void init(){
        usersRepo = usersRepository;
    }

    public static DbUser getCurrentUser() {
//        User ui = (User) SecurityContextHolder.getContext()
//                .getAuthentication().getPrincipal();
//
//        DbUser u = new DbUser();
//        u.setUsername(ui.getUsername());

//        return u;
        return getCurrentUsersFromDb();
    }

    public static DbUser getCurrentUsersFromDb() {
        User ui = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        DbUser u = usersRepo.getOne(ui.getUsername());
        return u;
    }
}
