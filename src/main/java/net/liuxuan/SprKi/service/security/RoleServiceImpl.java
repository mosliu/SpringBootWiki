package net.liuxuan.SprKi.service.security;


import net.liuxuan.SprKi.entity.security.Role;
import net.liuxuan.SprKi.repository.security.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.security.RoleServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/03/20 11:49
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-03-20  |    Moses        |     Created
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private static Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    RoleRepository roleRepository;


    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public Role findRoleById(String id) {
        Role role = roleRepository.findOne(id);
        return role;
    }

    public void deleteRoleById(String id) {
        roleRepository.findOne(id).setDisabled(true);
    }
}