package net.liuxuan.supportsystem.entity.labthink;

import net.liuxuan.supportsystem.repository.labthink.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyEditorSupport;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.labthink.DepartmentEditor
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/16 11:12
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/16  |    Moses       |     Created
 */
public class DepartmentEditor extends PropertyEditorSupport {

    @Autowired
    private DepartmentRepository repository;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null) {
            Department one = repository.findOne(Long.valueOf(text));
            setValue(one);
        } else {
            setValue(null);
        }
    }

}
