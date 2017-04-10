package net.liuxuan.SprKi.repository.security;

import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.security.Role;
import net.liuxuan.SprKi.entity.test.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
* ***************************************************************************
* 源文件名:  net.liuxuan.SprKi.repository.security.RoleRepository
* 功能:
* 版本:	@version 1.0
* 编制日期: 2017/03/20 11:49
* 修改历史: (主要历史变动原因及说明)
* YYYY-MM-DD |    Author      |	 Change Description
* 2017-03-20  |    Moses        |     Created
*/

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {
    List<Role> findByRolename(String  rolename);

    List<Role> findByRolenameNot(String  roleNotName);
    List<Role> findByDisabledFalse();

    List<Role> findByRolenameNotOrderByRolename(String roleNotName);

    @Query("SELECT DISTINCT rolename FROM Role WHERE disabled = false ")
    List<String> findAllRoles();

}


