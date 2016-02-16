package net.liuxuan.spring.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.ThymeleafConfig
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/15 13:51
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/15 |    Moses       |     Created
 */
@Configuration
public class ThymeleafConfiguration {


    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
    //    private static final String VIEWS = "/WEB-INF/views/";
    private static final String VIEWS = "/templates/";


    //模版解析器
    @Bean
    public TemplateResolver servletContextTemplateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();

//        templateResolver.setPrefix(VIEWS);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        templateResolver.setOrder(2);
        return templateResolver;
    }

    @Bean
    public TemplateResolver classLoaderTemplateResolver() {
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();

//        templateResolver.setPrefix(VIEWS);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        templateResolver.setOrder(3);
        return templateResolver;
    }

    /**
     * 花了大量的时间，最后分析出来应该用的是SpringResourceTemplateResolver。被网上众多资料误导了！
     *
     * @return
     */
    @Bean
    public TemplateResolver springResourceTemplateResolver() {
        TemplateResolver templateResolver = new SpringResourceTemplateResolver();

        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);
        templateResolver.setOrder(1);
        return templateResolver;
    }

    //spring的模版引擎
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        Set<ITemplateResolver> resolvers = new HashSet<ITemplateResolver>();
        resolvers.add(springResourceTemplateResolver());
        resolvers.add(classLoaderTemplateResolver());
        resolvers.add(servletContextTemplateResolver());

        templateEngine.setTemplateResolvers(resolvers);

//        templateEngine.setTemplateResolver(servletContextTemplateResolver());
//        templateEngine.addDialect(new org.thymeleaf.spring4.dialect.SpringStandardDialect());
        templateEngine.addDialect(new nz.net.ultraq.thymeleaf.LayoutDialect());
        templateEngine.addDialect(new net.sourceforge.pagesdialect.PagesDialect());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }


    //    //ThymeleafViewResolver页面解析器
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//
//        resolver.setTemplateEngine(templateEngine());
//        resolver.setCharacterEncoding("UTF-8");
//        String[] views = {"*.html", "*.xhtml", "templates/*"};
//        resolver.setViewNames(views);
//        return resolver;
//
//    }


}
