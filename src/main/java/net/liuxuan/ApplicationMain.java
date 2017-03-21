/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan;

import net.liuxuan.SprKi.entity.Banner;
import net.liuxuan.SprKi.repository.BannerRepository;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;



/**
 * Created by Moses on 2016/2/3.
 */


/*其中base-package为需要扫描的包（含所有子包）
     @Service用于标注业务层组件，
     @Controller用于标注控制层组件（如struts中的action）,
     @Repository用于标注数据访问组件，即DAO组件，
     @Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
*/

//SpringApplication是Spring Boot框架中描述Spring应用的类，它的run()方法会创建一个Spring应用上下文（Application Context）。
// 另一方面它会扫描当前应用类路径上的依赖，
// 例如本例中发现spring-webmvc（由 spring-boot-starter-web传递引入）在类路径中，
// 那么Spring Boot会判断这是一个Web应用，并启动一个内嵌的Servlet容器（默认是Tomcat）用于处理HTTP请求。



//“@EnableAutoConfiguration”注解的作用在于
// 让 spring boot 根据应用所声明的依赖来对 spring 框架进行自动配置，这就减少了开发人员的工作量。

@SpringBootApplication(exclude = {GsonAutoConfiguration.class}) //等同于 @Configuration @EnableAutoConfiguration @ComponentScan
//@Configuration
//@ComponentScan(basePackages = "net.liuxuan.spring.security , net.liuxuan.spring.mvc.** , ")
//@EnableAutoConfiguration()
//@ImportResource({ "classpath:config/webSecurityConfig.xml" })
//@EnableConfigurationProperties({DBSettings.class})
@EnableJpaRepositories(basePackages = "net.liuxuan.SprKi.repository",
        entityManagerFactoryRef ="entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@EntityScan({"net.liuxuan.SprKi.entity","net.liuxuan.springboottest.message"})
//@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Transactional
//@EnableScheduling
public class ApplicationMain extends SpringBootServletInitializer {

    private static Logger log  = LoggerFactory.getLogger(ApplicationMain.class);


    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ApplicationMain.class);
        //app.setBannerMode(Banner.Mode.OFF);
        ApplicationContext ctx = app.run(args);


//        String beanNames[] = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }

//        SpringApplication.run(ApplicationMain.class, args);

        log.info("\r\n==============\r\nApplication Started\n==============");

        mainTest();
//





    }

    private static void mainTest() {


        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");

//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationMain.class);
//        return super.configure(application);
    }


//    @Bean
//    public DataSource dataSource() {
//
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        return builder.setType(EmbeddedDatabaseType.HSQL).build();
//    }
//
//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.acme.domain");
//        factory.setDataSource(dataSource());
//        factory.afterPropertiesSet();
//
//        return factory.getObject();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//
//        JpaTransactionManager txManager = new JpaTransactionManager();
//        txManager.setEntityManagerFactory(entityManagerFactory());
//        return txManager;
//    }



//    @Bean
//    public CommandLineRunner demo(SecurityLogRepository repository){
//
//        return (args) -> {
//            SecurityLog slog = new SecurityLog();
//            slog.setUsername("");
//            slog.setLogIp("0.0.0.0");
//            slog.setLogUrl("/");
//            slog.setLogTime(new Date());
////        slog.setAction(LogActionType.CREATE_OR_UPDATE);
//            slog.setAction(LogActionType.STARTUP);
//            slog.setLogInfo("");
//            slog.setLogLevel(LogLevel.SYSTEMACTIVITY);
//            slog.setOperation("System staru up");
//            slog.setUrl("/");
//            repository.saveAndFlush(slog);
////            securityLogService.saveSecurityLog(slog);
//
//        };
//    }
//    @Bean
//    @Transactional(propagation = Propagation.NEVER)
//    public String  demo1(BannerRepository repository,SessionFactory sessionFactory ){
////        entityManager.getTransaction().begin();
//        sessionFactory.openSession();
//        Banner b = new Banner();
//        b.setName("11");
//        b.setId(11L);
//        repository.saveAndFlush(b);
//        return "";
//    }

}
