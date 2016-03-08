package net.liuxuan.spring.mvc.utilsinit;

import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.utilsinit.MyJobOne
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/7 14:41
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/7  |    Moses       |     Created
 */
@Service("jobone")
public class MyJobOne {
    protected void myTask() {
        System.out.println("This is my task");
    }
}
