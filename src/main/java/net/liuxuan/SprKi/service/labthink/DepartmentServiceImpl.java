package net.liuxuan.SprKi.service.labthink;

import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.repository.labthink.DepartmentRepository;
import net.liuxuan.spring.constants.JPAConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.service.labthink.DepartmentServiceImpl
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/14 11:09
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/14  |    Moses       |     Created
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {


    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findBydepartmentNameNot(JPAConstants.DELETEDOBJECTSTR);
//        return departmentRepository.findAll();
    }

    @Override
    public boolean checkDepartmentExists(String departmentname) {
        List<Department> departmentList = departmentRepository.findBydepartmentName(departmentname);
        if (departmentList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    public boolean deleteDepartmentById(String sid) {
        if (NumberUtils.isNumber(sid)) {
            Long id = Long.parseLong(sid);
            Department obj = departmentRepository.getOne(id);
            if (obj != null) {
                obj.setDepartmentName(JPAConstants.DELETEDOBJECTSTR);
                obj.setDepartmentNameCN(JPAConstants.DELETEDOBJECTSTR);
                return true;
            }
        }
        return false;

    }

    @Override
    public Department getDepartmentById(Long id) {
        Department obj = departmentRepository.getOne(id);
        return obj;
    }
}
