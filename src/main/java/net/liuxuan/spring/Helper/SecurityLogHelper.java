package net.liuxuan.spring.Helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import net.liuxuan.supportsystem.entity.security.LogLevel;
import net.liuxuan.supportsystem.entity.security.SecurityLog;
import net.liuxuan.supportsystem.service.security.SecurityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.SecurityLogHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/16 13:11
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/16  |    Moses       |     Created
 */
@Component
public class SecurityLogHelper {
    private static SecurityLogService securityLogService;

    @Autowired
    public SecurityLogHelper(SecurityLogService securityLogService){
        SecurityLogHelper.securityLogService = securityLogService;
    }

    /**
     * 记录登陆日志
     *
     * @param request 调用者的request
     * @param action action类型，建议使用LogActionType中定义的
     * @param entity 相关实体类
     * @param operation 操作记录
     * @param url 访问地址
     */
    public static void LogLogStatus(HttpServletRequest request,String action, Object entity, String operation,String url) {
        log(request, action, entity, operation,LogLevel.LOGACTIVITYS,url);
    }

    /**
     * 记录活动日志
     *
     * @param request 调用者的request
     * @param action action类型，建议使用LogActionType中定义的
     * @param entity 相关实体类
     * @param operation 操作记录
     * @param url 访问地址
     */
    public static void LogActivity(HttpServletRequest request,String action, Object entity, String operation,String url) {
        log(request, action, entity, operation,LogLevel.ACTIVITYS,url);
    }
    /**
     * 记录高权限日志
     *
     * @param request 调用者的request
     * @param action action类型，建议使用LogActionType中定义的
     * @param entity 相关实体类
     * @param operation 操作记录
     * @param url 访问地址
     */
    public static void LogHIGHRIGHT(HttpServletRequest request,String action, Object entity, String operation,String url) {
        log(request, action, entity, operation,LogLevel.HIGHRIGHT,url);
    }

    public static void log(HttpServletRequest request,String action, Object entity, String operation,String logLevel,String url) {
//        Gson gson = EntityGsonHelper.goGsonwithEntityCheck(entity.getClass());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());


        SecurityLog slog = new SecurityLog();
//        slog.setAction(request.getMethod());
        slog.setUsername(request.getRemoteUser());
        slog.setLogIp(RequestHelper.getIpAddr(request));
        slog.setLogUrl(request.getRequestURI());
        slog.setLogTime(new Date());
//        slog.setAction(LogActionType.CREATE_OR_UPDATE);
        slog.setAction(action);

//        slog.setLogInfo(gson.toJson(entity).toString());
        if(entity==null){
            entity = "NULL ENTITY";
        }
        try {
            slog.setLogInfo(mapper.writeValueAsString(entity));
            slog.setLogInfoType(entity.getClass().getName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        slog.setLogLevel(logLevel);
        slog.setOperation(operation);
        slog.setUrl(url);

        securityLogService.saveSecurityLog(slog);
    }
}
