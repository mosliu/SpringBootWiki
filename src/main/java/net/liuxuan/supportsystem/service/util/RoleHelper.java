package net.liuxuan.supportsystem.service.util;

import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.supportsystem.entity.CMSContent;
import net.liuxuan.supportsystem.entity.labthink.Department;
import net.liuxuan.supportsystem.entity.security.DbUser;
import net.liuxuan.supportsystem.entity.security.Role;
import net.liuxuan.supportsystem.service.labthink.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2010-2018.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.supportsystem.service.util.RoleHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2018/3/9 8:28
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2018/3/9  |    Moses       |     Created
 */
@Service
@Transactional
public class RoleHelper {

    @Autowired
    private DepartmentService departmentService;
    /**
     * Judge if the User has role to access the FAQContent.
     *
     * @param rolenames the rolenames
     * @param dept      the faq
     * @return the boolean
     */
    public boolean hasDepartmentRole(Set<String> rolenames, Department dept) {
        String deparmentRoleName = departmentService.getDeparmentRoleName(dept);
        if (rolenames.contains(deparmentRoleName)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Judge if the User has role to access the FAQContent.
     *
     * @param dept the Department
     * @return the boolean
     */
    public boolean hasDepartmentRole(Department dept) {
        List<Role> currentUserRoles = SystemHelper.getCurrentUserRoles();
        Set<String> rolenames = currentUserRoles.stream().map(e -> e.getRolename()).collect(Collectors.toSet());
        return hasDepartmentRole(rolenames, dept);
    }

    /**
     * Is admin boolean.
     *
     * @param rolenames the rolenames
     * @return the boolean
     */
    public boolean isAdmin(Set<String> rolenames) {
//        if (rolenames.contains("ROLE_ADMIN")) {
//            return true;
//        }
//        return false;
        return rolenames.contains("ROLE_ADMIN");
    }

    /**
     * Is admin boolean.
     *
     * @return the boolean
     */
    public boolean isAdmin() {
//        List<Role> currentUserRoles = SystemHelper.getCurrentUserRoles();
//        Set<String> rolenames = currentUserRoles.stream().map(e -> e.getRolename()).collect(Collectors.toSet());
//        return isAdmin(rolenames);
        return SystemHelper.isCurrentUserAdmin();
    }

    /**
     * Is the faq's author
     *
     * @param content the faq
     * @return the boolean
     */
    public boolean isAuthor(CMSContent content) {
        DbUser currentUser = SystemHelper.getCurrentUser();
        if (content.getAuthor().getUsername().equals(currentUser.getUsername())) {
            return true;
        } else {
            return false;
        }
    }

}
