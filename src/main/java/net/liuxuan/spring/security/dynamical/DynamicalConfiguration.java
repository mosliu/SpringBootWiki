package net.liuxuan.spring.security.dynamical;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.Filter;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.dynamical.DynamicalConfiguration
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/3/15 11:24
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/3/15  |    Moses       |     Created
 */
@Configuration
public class DynamicalConfiguration {

//    @Autowired
//    AuthenticationManager authenticationManager;



//    @Bean(name="customSecurityFilter")
    public Filter customSecurityFilter() {
        CustomSecurityFilter customSecurityFilter = new CustomSecurityFilter(securityMetadataSource());
        customSecurityFilter.setAccessDecisionManager(myAccessDecisionManager());
//        customSecurityFilter.setAuthenticationManager(authenticationManager);
//        customSecurityFilter.setRejectPublicInvocations(false);
        return customSecurityFilter;
    }

//    @Bean(name="securityMetadataSource")
    public FilterInvocationSecurityMetadataSource securityMetadataSource(){
        CustomInvocationSecurityMetadataSource customInvocationSecurityMetadataSource =new CustomInvocationSecurityMetadataSourceImpl();
        return customInvocationSecurityMetadataSource;
    }

//    @Bean(name="myAccessDecisionManager")
    public AccessDecisionManager myAccessDecisionManager (){
        CustomAccessDecisionManager customAccessDecisionManager = new CustomAccessDecisionManagerImpl();
        return customAccessDecisionManager;
    }

}
