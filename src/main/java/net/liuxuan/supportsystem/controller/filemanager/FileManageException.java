package net.liuxuan.supportsystem.controller.filemanager;

/**
 * Copyright (c) 2010-2018.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.supportsystem.controller.filemanager.FileManageException
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2018/3/1 11:12
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2018/3/1  |    Moses       |     Created
 */
public class FileManageException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FileManageException(String message) {
        super(message);
    }
}
