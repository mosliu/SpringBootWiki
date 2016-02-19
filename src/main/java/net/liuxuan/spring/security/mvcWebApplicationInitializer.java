package net.liuxuan.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.MvcWebApplicationInitializer
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/16 17:04
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/16 |    Moses       |     Created
 */
public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Specify {@link Configuration @Configuration}
     * and/or {@link Component @Component} classes to be
     * provided to the {@linkplain #createRootApplicationContext() root application context}.
     *
     * @return the configuration classes for the root application context, or {@code null}
     * if creation and registration of a root context is not desired
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
//        return new Class<?>[0];
//        return null;
        return new Class[] { RootConfiguration.class };
    }

    /**
     * Specify {@link Configuration @Configuration}
     * and/or {@link Component @Component} classes to be
     * provided to the {@linkplain #createServletApplicationContext() dispatcher servlet
     * application context}.
     *
     * @return the configuration classes for the dispatcher servlet application context or
     * {@code null} if all configuration is specified through root config classes.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
//        return new Class[] { MyWebConfig.class };
    }

    /**
     * Specify the servlet mapping(s) for the {@code DispatcherServlet} &mdash;
     * for example {@code "/"}, {@code "/app"}, etc.
     *
     * @see #registerDispatcherServlet(ServletContext)
     */
    @Override
    protected String[] getServletMappings() {
        return new String[0];
//        return new String[] { "/" };
    }

    /**
     * Specify filters to add and map to the {@code DispatcherServlet}.
     * @return an array of filters or {@code null}
     * @see #registerServletFilter(ServletContext, Filter)
     */
    protected Filter[] getServletFilters() {
//        return new Filter[] { new HiddenHttpMethodFilter(), new CharacterEncodingFilter()
        return null;
    }
}
