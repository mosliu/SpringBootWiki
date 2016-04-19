/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.spring.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;
import java.util.Locale;

/**
 * Created by Moses on 2016/2/5.
 * 9.自定义SpringMVC的拦截器，但不破坏Spring Boot HibernateInterceptort的默认加载和封装
 * http://blog.csdn.net/ba5189tsl/article/details/46912471
 */
@Configuration
public class GlobalWebConfiguration extends WebMvcConfigurerAdapter {
    //静态资源Handlers
    private static final String RESOURCES_LOCATION = "/static/";
    //    private static final String RESOURCES_LOCATION = "/";
    private static final String RESOURCES_HANDLER = RESOURCES_LOCATION + "**";

    private static Logger log = LoggerFactory.getLogger(GlobalWebConfiguration.class);

//    @Value("${SprKi.editor.acl}")
//    private String datasourceUrl;

//    @Autowired
//    private ThymeleafViewResolver thymeleafViewResolver;



    /**
     * Add {@link Converter}s and {@link Formatter}s in addition to the ones
     * registered by default.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter();
//        registry.addConverter(string2DevicesConverter);
    }

    /**
     * Configure the {@link HttpMessageConverter}s to use for reading or writing
     * to the body of the request or response. If no converters are added, a
     * default list of converters is registered.
     * <p><strong>Note</strong> that adding converters to the list, turns off
     * default converter registration. To simply add a converter without impacting
     * default registration, consider using the method
     * {@link #extendMessageConverters(java.util.List)} instead.
     *
     * @param converters initially an empty list of converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
//                .indentOutput(true)
//                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
//                .modulesToInstall(new ParameterNamesModule());
//        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
//        converters.add(new MappingJackson2XmlHttpMessageConverter(builder.xml().build()));
    }

    /**
     * Configure view resolvers to translate String-based view names returned from
     * controllers into concrete {@link org.springframework.web.servlet.View}
     * implementations to perform rendering with.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //The following is a Java config example that configures content negotiation view
        // resolution using FreeMarker HTML templates and Jackson as a default View for JSON rendering:
//        registry.enableContentNegotiation(new MappingJackson2JsonView());
//        registry.jsp("", ".jsp").viewNames("/jsp");
//        registry.order(0);
//        registry.viewResolver(thymeleafViewResolver);
    }

    /**
     * Provide a custom {@link Validator} instead of the one created by default.
     * The default implementation, assuming JSR-303 is on the classpath, is:
     * {@link org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean}.
     * Leave the return value as {@code null} to keep the default.
     */
    @Override
    public Validator getValidator() {
        return null;
    }

    /**
     * Configure content negotiation options.
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(true).useJaf(false)
//                .favorParameter(true).parameterName("mediaType")
//                .ignoreAcceptHeader(true)
////                .defaultContentType(MediaType.APPLICATION_JSON)
//                .defaultContentType(MediaType.TEXT_HTML)
//                .mediaType("xml", MediaType.APPLICATION_XML)
//                .mediaType("json", MediaType.APPLICATION_JSON);
//
//        configurer
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML);
    }

    /**
     * Configure asynchronous request handling options.
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    }

    /**
     * Helps with configuring HandlerMappings path matching options such as trailing slash match,
     * suffix registration, path matcher and path helper.
     * Configured path matcher and path helper instances are shared for:
     * <ul>
     * <li>RequestMappings</li>
     * <li>ViewControllerMappings</li>
     * <li>ResourcesMappings</li>
     * </ul>
     *
     * @since 4.0.3
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer
//                .setUseSuffixPatternMatch(true)
//                .setUseTrailingSlashMatch(false)
//                .setUseRegisteredSuffixPatternMatch(true)
//                .setPathMatcher(antPathMatcher())
//                .setUrlPathHelper(urlPathHelper());
    }

    /**
     * Add resolvers to support custom controller method argument types.
     * <p>This does not override the built-in support for resolving handler
     * method arguments. To customize the built-in support for argument
     * resolution, configure {@link RequestMappingHandlerAdapter} directly.
     *
     * @param argumentResolvers initially an empty list
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    /**
     * Add handlers to support custom controller method return value types.
     * <p>Using this option does not override the built-in support for handling
     * return values. To customize the built-in support for handling return
     * values, configure RequestMappingHandlerAdapter directly.
     *
     * @param returnValueHandlers initially an empty list
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    }

    /**
     * Configure the {@link HandlerExceptionResolver}s to handle unresolved
     * controller exceptions. If no resolvers are added to the list, default
     * exception resolvers are added instead.
     *
     * @param exceptionResolvers initially an empty list
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {

    }

    /**
     * Provide a custom {@link MessageCodesResolver} for building message codes
     * from data binding and validation error codes. Leave the return value as
     * {@code null} to keep the default.
     */
    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }


    /**
     * Add Spring MVC lifecycle interceptors for pre- and post-processing of
     * controller method invocations. Interceptors can be registered to apply
     * to all requests or be limited to a subset of URL patterns.
     * <p><strong>Note</strong> that interceptors registered here only apply to
     * controllers and not to resource handler requests. To intercept requests for
     * static resources either declare a
     * {@link org.springframework.web.servlet.handler.MappedInterceptor MappedInterceptor}
     * bean or switch to advanced configuration mode by extending
     * {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
     * WebMvcConfigurationSupport} and then override {@code resourceHandlerMapping}.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        log.info("\r\n==============\r\n{}\n==============",datasourceUrl);
        registry.addInterceptor(new TestUrlInterceptor());
//        registry.addInterceptor(testUrlInterceptor());
//        registry.addInterceptor(localeChangeInterceptor());
//        registry.addInterceptor(new LocaleInterceptor());
//        registry.addInterceptor(new ThemeChangeInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
//        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");

    }

    /**
     * Configure simple automated controllers pre-configured with the response
     * status code and/or a view to render the response body. This is useful in
     * cases where there is no need for custom controller logic -- e.g. render a
     * home page, perform simple site URL redirects, return a 404 status with
     * HTML content, a 204 with no content, and more.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCES_HANDLER).addResourceLocations(RESOURCES_LOCATION,"static/");
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("/", "classpath:/META-INF/public-web-resources/");
    }

    /**
     * Configure a handler to delegate unhandled requests by forwarding to the
     * Servlet container's "default" servlet. A common use case for this is when
     * the {@link DispatcherServlet} is mapped to "/" thus overriding the
     * Servlet container's default handling of static resources.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    /**
     * A hook for extending or modifying the list of converters after it has been
     * configured. This may be useful for example to allow default converters to
     * be registered and then insert a custom converter through this method.
     *
     * @param converters the list of configured converters to extend.
     * @since 4.1.3
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    }

    /**
     * Configure cross origin requests processing.
     *
     * @since 4.2
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    }

//    @Bean
//    public FilterRegistrationBean hiddenFilterRegistrationBean() {
//        return new FilterRegistrationBean(new HiddenHttpMethodFilter());
//    }


    //    @Bean
//    public LocaleResolver localeResolver() {
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    //    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * 国际化
     *
     * @return
     */
//    @Bean
//    @Qualifier("messageSource")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setBasename("i18n.u1wan-i18n");
        bundleMessageSource.setUseCodeAsDefaultMessage(true);
        return bundleMessageSource;
    }


    // Provides internationalization of messages
//    @Bean
//    public ReloadableResourceBundleMessageSource messageSource() {
//        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
//        source.setBasename("messages");
//        source.setDefaultEncoding("UTF-8");
//        return source;
//    }

    // Only needed if we are using @Value and ${...} when referencing properties
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer properties() {
//        PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
//        Resource[] resources = new ClassPathResource[] { new ClassPathResource(
//                "spring.properties") };
//        propertySources.setLocations(resources);
//        propertySources.setIgnoreUnresolvablePlaceholders(true);
//        return propertySources;
//    }


//    @Bean
//    public TestUrlInterceptor testUrlInterceptor() {
//        return new TestUrlInterceptor();
//    }

    //启用后有问题
//    @Bean(name = "multipartResolver")
//    public CommonsMultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setMaxUploadSize(1024 * 1024 * 100);
//        resolver.setMaxInMemorySize(1024 * 100);
//        resolver.setDefaultEncoding("utf-8");
//        return resolver;
//    }

    // Will map to a bean called "accounts/list" in "spreadsheet-views.xml"
//    @Bean(name="excelViewResolver")
//    public ViewResolver getXmlViewResolver() {
//        XmlViewResolver resolver = new XmlViewResolver();
//        resolver.setLocation(new ServletContextResource(servletContext,
//                "/WEB-INF/spring/spreadsheet-views.xml"));
//        resolver.setOrder(1);
//        return resolver;
//    }

    // Will map to the JSP page: "WEB-INF/views/accounts/list.jsp"
//    @Bean(name="jspViewResolver")
//    public ViewResolver getJspViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("templates/jsp/");
//        resolver.setSuffix(".jsp");
//        resolver.setOrder(2);
//        return resolver;
//    }


}
