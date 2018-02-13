package net.liuxuan.supportsystem.service.labthink;

import net.liuxuan.supportsystem.entity.labthink.Department;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.labthink.DepartmentService
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/14 11:08
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/14  |    Moses       |     Created
 */
public interface DepartmentService {
    /**
     * Gets all department.
     *
     * @return the all departments
     */
    List<Department> getAllDepartment();

    /**
     * Gets departments by name
     *
     * @param departmentname the departmentname
     * @return the all department
     */
    List<Department> getDepartmentsByName(String departmentname);

    /**
     * 检查是否每个部门都有了自己的权限，如果没有则自动创建
     */
    void checkDeparmentRole();

    /**
     * 获取该Department 对应的Rolename
     *
     * @param department the department
     * @return deparment role name
     */
    String getDeparmentRoleName(Department department);

    /**
     * 检查是否存在该Department对应的Role
     *
     * @param deparmentRoleName the deparment role name
     * @return boolean
     */
    boolean isDeparmentRoleExists(String deparmentRoleName);

    /**
     * 检查是否存在该Department对应的Role
     *
     * @param department the department
     * @return boolean
     */
    boolean isDeparmentRoleExists(Department department);

    /**
     * Check department exists boolean.
     *
     * @param departmentname the department name
     * @return the boolean
     */
    boolean checkDepartmentExists(String departmentname);

    /**
     * Save department.
     *
     * @param department the department
     * @return the department
     */
    Department saveDepartment(Department department);

    /**
     * Delete department by id.
     *
     * @param sid the sid
     * @return the boolean
     */
    boolean deleteDepartmentById(String sid);

    /**
     * Delete department by id.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteDepartmentById(Long id);

    /**
     * Gets department by id.
     *
     * @param id the id
     * @return the department by id
     */
    Department getDepartmentById(Long id);
}
