package net.liuxuan.spring.mvc;

import net.liuxuan.supportsystem.service.security.SecurityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.MvcServlet
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/15 11:07
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/15 |    Moses       |     Created
 */
@Component("mvcServlet")
public class MvcServlet implements Servlet {

    @Autowired
    SecurityLogService securityLogService;

    /**
     * @see javax.servlet.Servlet#destroy()
     */
    @Override
    public void destroy() {
        System.out.println("destroy...");
    }

    /**
     * @return
     * @see javax.servlet.Servlet#getServletConfig()
     */
    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * @return
     * @see javax.servlet.Servlet#getServletInfo()
     */
    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * @param arg0
     * @throws ServletException
     * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig arg0) throws ServletException {


        System.out.println("servlet init...");
    }

    /**
     * @param arg0
     * @param arg1
     * @throws ServletException
     * @throws IOException
     * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException,
            IOException {
        System.out.println("service...");
    }
}
