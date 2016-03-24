package net.liuxuan.spring.mvc.views;

//import com.github.dandelion.datatables.thymeleaf.dialect.DataTablesDialect;
//import com.github.dandelion.thymeleaf.dialect.DandelionDialect;
import freemarker.template.TemplateException;
import net.sourceforge.pagesdialect.PagesDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    private static Logger log =  LoggerFactory.getLogger(ThymeleafConfiguration.class);

    private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
    //    private static final String VIEWS = "/WEB-INF/views/";
    private static final String VIEWS = "templates/";


    //模版解析器
//    @Bean
    public TemplateResolver servletContextTemplateResolver() {
        TemplateResolver templateResolver = new ServletContextTemplateResolver();

//        templateResolver.setPrefix(VIEWS);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateResolver.setOrder(2);
        return templateResolver;
    }

//    @Bean
    public TemplateResolver classLoaderTemplateResolver() {
        TemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix(VIEWS);
//        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateResolver.setOrder(3);
        return templateResolver;
    }

    /**
     * 花了大量的时间，最后分析出来应该用的是SpringResourceTemplateResolver。被网上众多资料误导了！
     *
     * @return TemplateResolver
     */
//    @Bean
    public TemplateResolver springResourceTemplateResolver() {
        TemplateResolver templateResolver = new SpringResourceTemplateResolver();

        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateResolver.setOrder(1);
        return templateResolver;
    }

    //spring的模版引擎
//    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        Set<ITemplateResolver> resolvers = new HashSet<ITemplateResolver>();
//        resolvers.add(springResourceTemplateResolver());
        resolvers.add(classLoaderTemplateResolver());
        resolvers.add(servletContextTemplateResolver());

        templateEngine.setTemplateResolvers(resolvers);

//        templateEngine.setTemplateResolver(servletContextTemplateResolver());
//        templateEngine.addDialect(new org.thymeleaf.spring4.dialect.SpringStandardDialect());
//        templateEngine.addDialect(new nz.net.ultraq.thymeleaf.LayoutDialect());
        templateEngine.addDialect(new nz.net.ultraq.thymeleaf.LayoutDialect(new GroupingStrategy()));
        templateEngine.addDialect(new net.sourceforge.pagesdialect.PagesDialect());
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

//
    @Bean
    public BeanNameViewResolver beanViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(10);
        return resolver;
    }

//    @Bean
//    public MarshallingHttpMessageConverter marshallingMessageConverter() {
//        MarshallingHttpMessageConverter converter= new MarshallingHttpMessageConverter(
//                jaxb2Marshaller(),
//                jaxb2Marshaller()
//        );
//        List<MediaType> mediaTypes = new ArrayList<MediaType>();
//        mediaTypes.add(MediaType.APPLICATION_XML);
//        converter.setSupportedMediaTypes(mediaTypes);
//        return converter;
//    }


//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
//
//        converters.add(stringHttpMessageConverter());
//        converters.add(marshallingJsonConverter());
//        converters.add(marshallingMessageConverter());
//        restTemplate.setMessageConverters(converters);
//
//        return restTemplate;
//    }

//    @Bean
//    public StringHttpMessageConverter stringHttpMessageConverter(){
//        return new StringHttpMessageConverter();
//    }



//    @Bean
//    public ThymeleafTilesConfigurer tilesConfigurer() {
//        ThymeleafTilesConfigurer tilesConfigurer = new ThymeleafTilesConfigurer();
//        tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/tiles.xml"});
//        return tilesConfigurer;
//    }

//    @Bean
//    public TilesDialect tilesDialect(){
//        TilesDialect dialect = new TilesDialect();
//        return dialect;
//    }

//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        resolver.setViewClass(ThymeleafTilesView.class);
//        resolver.setOrder(2);
//        resolver.setCharacterEncoding("UTF-8");
//        String[] views = {"*.html","*.xhtml"};
//        resolver.setViewNames(views);
//        return resolver;
//    }




    //    //ThymeleafViewResolver页面解析器
//    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
//        resolver.setViewClass(ThymeleafTilesView.class);
//        resolver.setOrder(2);
//        resolver.setCharacterEncoding("UTF-8");
//        String[] views = {"*.html","*.xhtml"};
//        resolver.setViewNames(views);
//        String[] views = {"*.html", "*.xhtml", "templates/*","/*"};
//        String[] views = {"*.html", "*.xhtml", "templates/*","/**"};
//        resolver.setViewNames(views);
        //默认是null
//        String[] excludedviews = {"jsp/*"};
//        resolver.setExcludedViewNames(excludedviews);
        resolver.setOrder(20);
//        String[] viewNames = resolver.getViewNames();
//        if(viewNames!=null) {
//            for (String s : viewNames) {
//                log.info("##thymeleafViewResolver.views:{}", s);
//            }
//        }else{
//            log.info("##thymeleafViewResolver.views:null!!!");
//        }
        return resolver;

    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver irvr = new InternalResourceViewResolver();
        irvr.setViewClass(JstlView.class);
        irvr.setCache(false);
        irvr.setPrefix("classpath:/templates/");
        irvr.setSuffix(".jsp");
        String[] views = {"*jsp"};
        irvr.setViewNames(views);
        irvr.setOrder(1);

        return irvr;
    }


    //TODO 返回名必须是viewResolver  所以不起作用



    /**
     * Create the CNVR. Get Spring to inject the ContentNegotiationManager
     * created by the configurer (see previous method).
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(beanViewResolver());
        viewResolvers.add(thymeleafViewResolver());
        viewResolvers.add(internalResourceViewResolver());
        resolver.setViewResolvers(viewResolvers);
        return resolver;
    }


    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver()  {

        FreeMarkerViewResolver freeMarkerViewResolver= new FreeMarkerViewResolver();
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
        freeMarkerViewResolver.setCache(false);
        freeMarkerViewResolver.setOrder(3);
        freeMarkerViewResolver.setContentType("text/html; charset=utf-8");
        return freeMarkerViewResolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {

        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:/templates/");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setConfiguration(factory.createConfiguration());
        return result;
    }

//
//    @Bean(name = {"jsonView"})
//    public MappingJackson2JsonView jsonView(){
////        log.debug("======jasonView registered");
//        return new MappingJackson2JsonView();
//    }

    /*******************Dialect 注册**********************/
    @Bean
    @ConditionalOnMissingBean
    public PagesDialect pagesDialect() {
        return new PagesDialect();
    }
//    @Bean
//    @ConditionalOnMissingBean
//    public DandelionDialect dandelionDialect() {
//        return new DandelionDialect();
//    }
//    @Bean
//    @ConditionalOnMissingBean
//    public DataTablesDialect dataTablesDialect() {
//        return new DataTablesDialect();
//    }
//    @Autowired
//    private ThymeleafProperties properties;
//
//    @Bean
//    public String voidMethod(){
//        log.info("!!!!!!!!!!!!!!!!properties encoding:{}",this.properties.getEncoding().name());
//        return null;
//    }

}
