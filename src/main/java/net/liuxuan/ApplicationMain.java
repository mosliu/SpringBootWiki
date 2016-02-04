package net.liuxuan;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Moses on 2016/2/3.
 */

//SpringApplication是Spring Boot框架中描述Spring应用的类，它的run()方法会创建一个Spring应用上下文（Application Context）。另一方面它会扫描当前应用类路径上的依赖，例如本例中发现spring-webmvc（由 spring-boot-starter-web传递引入）在类路径中，那么Spring Boot会判断这是一个Web应用，并启动一个内嵌的Servlet容器（默认是Tomcat）用于处理HTTP请求。
// 注解“@RestController”和”@RequestMapping”由 Spring MVC 提供，用来创建 REST 服务。
// 这两个注解和 Spring Boot 本身并没有关系。
//“@EnableAutoConfiguration”注解的作用在于
// 让 Spring Boot 根据应用所声明的依赖来对 Spring 框架进行自动配置，这就减少了开发人员的工作量。

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration

@SpringBootApplication //等同于 @Configuration @EnableAutoConfiguration @ComponentScan
public class ApplicationMain {
    private static Logger log =  LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ApplicationMain.class);
        app.setBannerMode(Banner.Mode.OFF);
        ApplicationContext ctx = app.run(args);

//        String beanNames[] = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }

//        SpringApplication.run(ApplicationMain.class, args);
        String a = "Application Started";
        log.warn(a);

        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");
//
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);
    }
}
