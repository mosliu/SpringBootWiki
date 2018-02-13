package net.liuxuan.spring.security;

import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.MyAuthenticationFailureHandler
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/22 10:03
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/22  |    Moses       |     Created
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static Logger log = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);
    private static String FailureUrl = "/login?error&q1";


    public MyAuthenticationFailureHandler() {
        super(FailureUrl);
//        log.trace("Set Default Failure Url to : {}",FailureUrl);
    }

    public MyAuthenticationFailureHandler(String defaultFailureUrl) {
        super(defaultFailureUrl);
//        log.trace("Set DefaultFailure Url to : {}",defaultFailureUrl);
        FailureUrl = defaultFailureUrl;
    }

    /**
     * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise
     * returns a 401 error code.
     * <p>
     * If redirecting or forwarding, {@code saveException} will be called to cache the
     * exception for use in the target view.
     *
     * @param request
     * @param response
     * @param exception
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//        super.onAuthenticationFailure(request, response, exception);
        saveException(request, exception);

        SecurityLogHelper.LogLogStatus(request, LogActionType.LOGIN,request.getParameterMap(),"登陆失败","");

        request.getSession().setAttribute("errorDetail","Login Error,Please Check!");
//        request.getSession().setAttribute("errorMessage",exception.getMessage());
        request.getSession().setAttribute("errorMessage","登录失败！");

//        SecurityLogHelper.LogActivity(request, LogActionType.LOGIN,auth,"登录失败","");
//        logger.trace("===Redirecting to " + FailureUrl);

        this.getRedirectStrategy().sendRedirect(request, response, FailureUrl);
    }


}
