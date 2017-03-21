package net.liuxuan.SprKi.entity.security;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.security.LogLevel
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/16 11:40
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/16  |    Moses       |     Created
 */
public class LogLevel {
    /**
     * 记录闲杂信息。
     */
    public static final String VERBOSE = "VERBOSE";
    /**
     * 记录闲杂信息。
     */
    public static final String SYSTEMACTIVITY = "SYSTEMACTIVITY";

    /**
     * 登陆日志信息。
     */
    public static final String LOGACTIVITYS = "LOGACTIVITYS";

    /**
     * 记录活动信息。
     */
    public static final String ACTIVITYS = "ACTIVITYS";
    /**
     * 记录正常信息。正常情况下不可删除
     */
    public static final String NORMAL = "NORMAL";
    /**
     * 记录高权限信息，不可删除
     */
    public static final String HIGHRIGHT = "HIGHRIGHT";
}
