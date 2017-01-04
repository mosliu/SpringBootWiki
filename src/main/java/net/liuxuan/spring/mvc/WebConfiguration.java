package net.liuxuan.spring.mvc;

//import com.github.dandelion.core.web.DandelionFilter;
//import com.github.dandelion.core.web.DandelionServlet;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.annotation.Resource;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.WebConfig
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/15 11:13
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/15 |    Moses       |     Created
 */


@Configuration
public class WebConfiguration {


    @Bean
    public FilterRegistrationBean mvcfilterRegistrationBean(MvcFilter mvcFilter){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(mvcFilter);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.setName("MvcFilter");
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/bb");
//        filterRegistrationBean.addUrlPatterns("/url/*");
        return filterRegistrationBean;
    }

//    @Bean
//    public FilterRegistrationBean dandelionFilterRegistrationBean(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        DandelionFilter dandelionFilter =new DandelionFilter();
//        filterRegistrationBean.setFilter(dandelionFilter);
//        filterRegistrationBean.setEnabled(true);
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    public ServletRegistrationBean dandelionServletRegistrationBean(){
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//        DandelionServlet dandelionServlet = new DandelionServlet();
//        servletRegistrationBean.setServlet(dandelionServlet);
//        servletRegistrationBean.setLoadOnStartup(2);
//        servletRegistrationBean.addUrlMappings("/dandelion-assets/*");
////        servletRegistrationBean.
//        return servletRegistrationBean;
//    }

//    @Bean
//    public ServletRegistrationBean mvcServletRegistrationBean(MvcServlet mvcServlet){
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
//        servletRegistrationBean.addUrlMappings("/jsp/");
//        servletRegistrationBean.setServlet(mvcServlet);
////        servletRegistrationBean.
//        return servletRegistrationBean;
//    }
//    @Bean
//    public SimpleMappingExceptionResolver springExceptionResolver(){
//        return new MvcSimpleMappingExceptionResolver();
//    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean(OpenSessionInViewFilter openSessionInViewFilter){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(openSessionInViewFilter);
//        filterRegistrationBean.setEnabled(true);
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;
//    }
//
//    @Bean
//    public OpenSessionInViewFilter openSessionInViewFilter(){
//        return new OpenSessionInViewFilter();
//    }



}
