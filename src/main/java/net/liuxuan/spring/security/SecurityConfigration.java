package net.liuxuan.spring.security;

import net.liuxuan.SprKi.service.security.SecurityUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.switchuser.SwitchUserFilter;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.SecurityConfigration
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/15 14:28
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/15 |    Moses       |     Created
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfigration extends WebSecurityConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(SecurityConfigration.class);
    @Autowired
    SecurityUserDetailsServiceImpl securityUserDetailsService;
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
//    @Resource
//    MyLoginUrlAuthEntryPoint myLoginUrlAuthEntryPoint;
    @Resource
    MyLogoutHandler myLogoutHandler;
//    @Resource
//    IPRoleAuthenticationFilter iPRoleAuthenticationFilter;
    @Autowired
    private DataSource dataSource;

    /**
     * Override this method to configure {@link WebSecurity}. For example, if you wish to
     * ignore certain requests.
     */
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .exceptionHandling().authenticationEntryPoint(myLoginUrlAuthEntryPoint).and()
//                .addFilterBefore(iPRoleAuthenticationFilter,AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/css/**", "/fonts/**", "/js/**", "/favicon.ico").permitAll()

//                .antMatchers("/").anonymous()
                .antMatchers("/msg/**").hasRole("USER")
                .antMatchers("/editor/**").authenticated()
                .and()

//                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//                .anyRequest().authenticated().and()
//                .anyRequest().fullyAuthenticated()
//                The method formLogin().permitAll() statement instructs Spring Security to allow any access to any URL
//                 (i.e. /login and /login?error) associated to formLogin().
                .formLogin().loginPage("/login").successHandler(myAuthenticationSuccessHandler).failureUrl("/login?error").permitAll()
                .and()

                .logout()
//                .logoutSuccessHandler()
//                .addLogoutHandler(myLogoutHandler)
//                .logoutSuccessUrl("/logout")
                .permitAll().and()
                .headers().frameOptions().sameOrigin().and()
                .rememberMe().and()
                .userDetailsService(securityUserDetailsService)
                .csrf().disable()
//                .sessionManagement().invalidSessionUrl("/invalid").and()
//                .jee().mappableRoles("USER", "ADMIN")
//                .addFilterBefore(iPRoleAuthenticationFilter,AnonymousAuthenticationFilter.class)

        ;


//
//        http.authorizeRequests().accessDecisionManager(accessDecisionManager())
//                .expressionHandler(webSecurityExpressionHandler())
//                .antMatchers("/**/*.do*").hasRole("USER")
//                .antMatchers("/**/*.htm").hasRole("ADMIN").and()
//                .exceptionHandling().accessDeniedPage("/login");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(this.dataSource)
//                .and().userDetailsService(securityUserDetailsService)

//                .withDefaultSchema()
//                .withUser("user").password("user").roles("USER").and()
//                .withUser("admin").password("password").roles("USER", "ADMIN")
        ;

    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        logger.info("UserDetailsService");
//        UserDetailsService userDetailsService = new UserDetailsService();
//        return userDetailsService;
//    }

//    @Bean
//    public LoggerListener loggerListener() {
//        logger.info("org.springframework.security.authentication.event.LoggerListener");
//        LoggerListener loggerListener = new LoggerListener();
//
//        return loggerListener;
//    }
//
//    @Bean
//    public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
//        logger.info("org.springframework.security.access.event.LoggerListener");
//        org.springframework.security.access.event.LoggerListener eventLoggerListener = new org.springframework.security.access.event.LoggerListener();
//
//        return eventLoggerListener;
//    }
//
//    /*
//     *
//     * 这里可以增加自定义的投票器
//     */
//    @SuppressWarnings("rawtypes")
//    @Bean(name = "accessDecisionManager")
//    public AccessDecisionManager accessDecisionManager() {
//        logger.info("AccessDecisionManager");
//        List<AccessDecisionVoter> decisionVoters = new ArrayList<AccessDecisionVoter>();
//        decisionVoters.add(new RoleVoter());
//        decisionVoters.add(new AuthenticatedVoter());
//        decisionVoters.add(webExpressionVoter());// 启用表达式投票器
//
//        AffirmativeBased accessDecisionManager = new AffirmativeBased(decisionVoters);
//
//        return accessDecisionManager;
//    }
//
//    /*
//     * 表达式控制器
//     */
//    @Bean(name = "expressionHandler")
//    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        logger.info("DefaultWebSecurityExpressionHandler");
//        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
//        return webSecurityExpressionHandler;
//    }
//
//    /*
//     * 表达式投票器
//     */
//    @Bean(name = "expressionVoter")
//    public WebExpressionVoter webExpressionVoter() {
//        logger.info("WebExpressionVoter");
//        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
//        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
//        return webExpressionVoter;
//    }
}
