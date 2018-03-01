package net.liuxuan.supportsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.exceptions.ContentNotFoundException
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/4 15:49
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/4  |    Moses       |     Created
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Such Content")  // 404
public class ContentNotFoundException extends RuntimeException {

    private Long id;

    public ContentNotFoundException(String msg, Long id) {
        super(msg);
        this.id = id;
    }
}
