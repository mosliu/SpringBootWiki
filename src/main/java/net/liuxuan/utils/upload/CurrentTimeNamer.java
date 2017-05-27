package net.liuxuan.utils.upload;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.utils.upload.CurrentTimeNamer
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/5/15 14:13
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/5/15  |    Moses       |     Created
 */
public class CurrentTimeNamer implements UploadFileNameGenerater{
    @Override
    public String generate(String name) {
        return System.currentTimeMillis() + "_" + name;
    }
}