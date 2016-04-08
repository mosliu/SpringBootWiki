package net.liuxuan.spring.security;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import net.liuxuan.SprKi.service.security.SecurityUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

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
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfigration extends WebSecurityConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(SecurityConfigration.class);
    @Autowired
    SecurityUserDetailsServiceImpl securityUserDetailsService;
    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;
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
                .antMatchers("/resources/**", "/static/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/ui/**", "/images/**", "/tinymce/**", "/ueditor/**", "/css/**", "/fonts/**", "/js/**", "/favicon.ico").permitAll()
                .antMatchers("/msg/**").hasRole("USER")
                .antMatchers("/editor/**").authenticated();
//                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//                .anyRequest().authenticated().and()
//                .anyRequest().fullyAuthenticated()
//                .antMatchers("/").anonymous()
//                .exceptionHandling().authenticationEntryPoint(myLoginUrlAuthEntryPoint).and()
//                .addFilterBefore(iPRoleAuthenticationFilter,AnonymousAuthenticationFilter.class)

//                The method formLogin().permitAll() statement instructs Spring Security to allow any access to any URL
//                 (i.e. /login and /login?error) associated to formLogin().
        http.formLogin()
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
//                .failureUrl("/login?error")
                .authenticationDetailsSource(captchaAuthenticationDetailsSource())
                .permitAll();
        http.logout()
                .permitAll();
//                .logoutSuccessHandler()
//                .addLogoutHandler(myLogoutHandler)
//                .logoutSuccessUrl("/logout")
        http.headers().frameOptions().sameOrigin();
        http.rememberMe()
                .userDetailsService(securityUserDetailsService)
                .rememberMeParameter("remember-me")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(86400);
//                .rememberMe().rememberMeParameter("_spring_security_remember_me").userDetailsService(securityUserDetailsService).and()
        http.requestCache();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.userDetailsService(securityUserDetailsService);
        http.csrf().disable();
        http.authenticationProvider(insertCaptchaDaoAuthenticationProvider());
        //TODO <form action="./upload?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
// .sessionManagement().invalidSessionUrl("/invalid").and()
//                .jee().mappableRoles("USER", "ADMIN")
//                .addFilterBefore(iPRoleAuthenticationFilter,AnonymousAuthenticationFilter.class)
//
//        http.authorizeRequests().accessDecisionManager(accessDecisionManager())
//                .expressionHandler(webSecurityExpressionHandler())
//                .antMatchers("/**/*.do*").hasRole("USER")
//                .antMatchers("/**/*.htm").hasRole("ADMIN").and()
//                .exceptionHandling().accessDeniedPage("/login");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(this.dataSource);
//        auth.userDetailsService(securityUserDetailsService);
//        auth.authenticationProvider(insertCaptchaDaoAuthenticationProvider());

//                .and().userDetailsService(securityUserDetailsService)

//                .withDefaultSchema()
//                .withUser("user").password("user").roles("USER").and()
//                .withUser("admin").password("password").roles("USER", "ADMIN")

    }


    @Bean
    public CaptchaDaoAuthenticationProvider insertCaptchaDaoAuthenticationProvider() {
        CaptchaDaoAuthenticationProvider provider = new CaptchaDaoAuthenticationProvider();
        provider.setUserDetailsService(securityUserDetailsService);

        return provider;
    }

    @Bean
    public CaptchaAuthenticationDetailsSource captchaAuthenticationDetailsSource() {
        CaptchaAuthenticationDetailsSource captchaAuthenticationDetailsSource = new CaptchaAuthenticationDetailsSource();
        return captchaAuthenticationDetailsSource;
    }


//    @Autowired
//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(securityUserDetailsService);
//    }

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

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder;
//    }
//
//    @Bean
//    public UserDetailsService loginService(){
//        UserDetailsService service = new LoginService();
//        return service;
//    }
//
//    @Override
//    protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .jdbcAuthentication().and()
//                .eraseCredentials(true)
//                .userDetailsService(loginService())
//                .passwordEncoder(passwordEncoder());
//    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
//        tokenRepositoryImpl.setCreateTableOnStartup(true);
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

    @Bean(name = "captchaProducer")
    public Producer captchaProducer() {
        /*
            kaptcha.border  是否有边框  默认为true  我们可以自己设置yes，no
            kaptcha.border.color   边框颜色   默认为Color.BLACK
            kaptcha.border.thickness  边框粗细度  默认为1
            kaptcha.producer.impl   验证码生成器  默认为DefaultKaptcha
            kaptcha.textproducer.impl   验证码文本生成器  默认为DefaultTextCreator
            kaptcha.textproducer.char.string   验证码文本字符内容范围  默认为abcde2345678gfynmnpwx
            kaptcha.textproducer.char.length   验证码文本字符长度  默认为5
            kaptcha.textproducer.font.names    验证码文本字体样式  默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
            kaptcha.textproducer.font.size   验证码文本字符大小  默认为40
            kaptcha.textproducer.font.color  验证码文本字符颜色  默认为Color.BLACK
            kaptcha.textproducer.char.space  验证码文本字符间距  默认为2
            kaptcha.noise.impl    验证码噪点生成对象  默认为DefaultNoise
            kaptcha.noise.color   验证码噪点颜色   默认为Color.BLACK
            kaptcha.obscurificator.impl   验证码样式引擎  默认为WaterRipple
            kaptcha.word.impl   验证码文本字符渲染   默认为DefaultWordRenderer
            kaptcha.background.impl   验证码背景生成器   默认为DefaultBackground
            kaptcha.background.clear.from   验证码背景颜色渐进   默认为Color.LIGHT_GRAY
            kaptcha.background.clear.to   验证码背景颜色渐进   默认为Color.WHITE
            kaptcha.image.width   验证码图片宽度  默认为200
            kaptcha.image.height  验证码图片高度  默认为50
         */

        Properties props = new Properties();
        props.put("kaptcha.border", "yes");
        props.put("kaptcha.border.color", "105,179,90");
        props.put("kaptcha.textproducer.font.color", "blue");
        props.put("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        props.put("kaptcha.textproducer.char.size", "45");
        props.put("kaptcha.textproducer.char.length", "4");
        props.put("kaptcha.image.width", "125");
        props.put("kaptcha.image.height", "45");
        props.put("kaptcha.session.key", "code");
        Config config = new Config(props);
        Producer captchaProducer = config.getProducerImpl();
        return captchaProducer;
    }


}
