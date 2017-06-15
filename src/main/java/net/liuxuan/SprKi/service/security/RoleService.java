package net.liuxuan.SprKi.service.security;


import net.liuxuan.SprKi.entity.security.Role;

import java.util.List;
import java.util.Map;


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

    /**
     * Gets all role.
     *
     * @return the all role
     */
    List<Role> getAllRole();

    /**
     * Save role.
     *
     * @param role the role
     */
    void saveRole(Role role);

    /**
     * Find role by id role.
     *
     * @param id the id
     * @return the role
     */
    Role findRoleById(String id);

    /**
     * Find all role names list.
     *
     * @return the list
     */
    List<String> findAllRoleNames();

    /**
     * Delete role by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteRoleById(String id);

    /**
     * Check role exists boolean.
     *
     * @param rolename the rolename
     * @return the boolean
     */
    boolean checkRoleExists(String rolename);

    /**
     * Update auths map.
     *
     * @param rolename   the rolename
     * @param authArrays the auth arrays
     * @return the map
     */
    Map<String,Object> updateAuths(String rolename, String[] authArrays);
}