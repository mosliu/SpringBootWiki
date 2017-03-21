package net.liuxuan.SprKi.service.security;


import net.liuxuan.SprKi.entity.security.Role;

import java.util.List;


/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.security.RoleService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/03/20 11:49
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-03-20  |    Moses        |     Created
 */
public interface RoleService {

    List<Role> getAllRole();

    void saveRole(Role role);

    Role findRoleById(String id);

    boolean deleteRoleById(String id);

    boolean checkRoleExists(String rolename);
}