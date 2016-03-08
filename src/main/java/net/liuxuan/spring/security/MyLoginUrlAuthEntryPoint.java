package net.liuxuan.spring.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.MyLoginUrlAuthenticationEntryPoint
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/19 10:31
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/19 |    Moses       |     Created
 */
@Component
public class MyLoginUrlAuthEntryPoint extends LoginUrlAuthenticationEntryPoint {
    private static Logger log =  LoggerFactory.getLogger(MyLoginUrlAuthEntryPoint.class);
    public static String loginFormUrl = "/login";

    public  MyLoginUrlAuthEntryPoint(){
        super(loginFormUrl);
        this.setUseForward(true);
    }


    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        String returnUrl = buildHttpReturnUrlForRequest(request);
        request.getSession().setAttribute("ru", returnUrl);
        log.debug("Backup return URL:{}",returnUrl);
        super.commence(request, response, authException);
    }


//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//                throws IOException, ServletException {
//        log.trace("!@!@$!@#%!#$%^!$^%$^@$%^@$#^@%$&*%*#%*$&");
//    }
    protected String buildHttpReturnUrlForRequest(HttpServletRequest request)
            throws IOException, ServletException {


        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
        urlBuilder.setScheme("http");
        urlBuilder.setServerName(request.getServerName());
        urlBuilder.setPort(request.getServerPort());
        urlBuilder.setContextPath(request.getContextPath());
        urlBuilder.setServletPath(request.getServletPath());
        urlBuilder.setPathInfo(request.getPathInfo());
        urlBuilder.setQuery(request.getQueryString());

        return urlBuilder.getUrl();
    }

}