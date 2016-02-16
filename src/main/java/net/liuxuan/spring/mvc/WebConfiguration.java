package net.liuxuan.spring.mvc;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public FilterRegistrationBean filterRegistrationBean(MvcFilter mvcFilter){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(mvcFilter);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/bb");
        return filterRegistrationBean;
    }
}
