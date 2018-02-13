package net.liuxuan.supportsystem.entity.security;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.security.LogActionType
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/16 11:35
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/16  |    Moses       |     Created
 */
public class LogActionType {
    /**
     * 登入
     */
    public static final String LOGIN = "LOGIN";
    /**
     * 登出
     */
    public static final String LOGOUT = "LOGOUT";
    /**
     * 创建实例使用
     */
    public static final String CREATE = "CREATE";

    /**
     * 更新实例使用
     */
    public static final String UPDATE = "UPDATE";

    /**
     * 创建更新实例使用
     */
    public static final String CREATE_OR_UPDATE = "CREATE_OR_UPDATE";

    /**
     * 创建更新实例使用
     */
    public static final String SEND_MSG = "SEND_MSG";

    /**
     * 删除实例使用
     */
    public static final String DELETE = "DELETE";


    /**
     * 禁用实例使用
     */
    public static final String DISABLE = "DISABLE";

    /**
     * 禁用实例使用
     */
    public static final String ENABLE = "ENABLE";
    /**
     * 访问实例使用
     */
    public static final String ACCESS = "ACCESS";

    /**
     * 后台操作
     */
    public static final String ADMIN = "ADMIN";

    /**
     * 创建实例使用
     */
    public static final String ADMIN_CREATE = "ADMIN_CREATE";

    /**
     * 更新实例使用
     */
    public static final String ADMIN_UPDATE = "ADMIN_UPDATE";

    /**
     * 创建更新实例使用
     */
    public static final String ADMIN_CREATE_OR_UPDATE = "ADMIN_CREATE_OR_UPDATE";

    /**
     * 删除实例使用
     */
    public static final String ADMIN_DELETE = "ADMIN_DELETE";


    /**
     * 禁用实例使用
     */
    public static final String ADMIN_DISABLE = "ADMIN_DISABLE";

    /**
     * 禁用实例使用
     */
    public static final String ADMIN_ENABLE = "ADMIN_ENABLE";


    /**
     * 后台操作
     */
    public static final String STARTUP = "STARTUP";

    /**
     * 新用户操作
     */
    public static final String USER_NEW = "USER_NEW";
    /**
     *删用户操作
     */
    public static final String USER_DELETE = "USER_DELETE";
    /**
     *删用户操作
     */
    public static final String USER_UPDATE = "USER_UPDATE";
    /**
     *改用户权限
     */
    public static final String USER_AUTH = "USER_AUTH";

}
