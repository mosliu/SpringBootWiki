package net.liuxuan.SprKi.service.labthink;

import net.liuxuan.SprKi.entity.labthink.Department;

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
     * @return the all department
     */
    List<Department> getAllDepartment();

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
     */
    void saveDepartment(Department department);

    /**
     * Delete department by id.
     *
     * @param sid the sid
     */
    boolean deleteDepartmentById(String sid);

    Department getDepartmentById(Long id);
}
