package net.liuxuan.spring.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.support.OpenSessionInViewInterceptor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.HibernateConfiguration
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/18 14:50
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/18 |    Moses       |     Created
 */
@Configuration
@EnableTransactionManagement
//@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {
//    @Autowired
//    private Environment environment;

    @Bean
    public OpenSessionInViewInterceptor openSessionInViewInterceptor(){
        return new OpenSessionInViewInterceptor();
    }

//    @Autowired
//    private DataSource dataSource;

//    @Autowired
//    private LocalSessionFactoryBean sessionFactory;



//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan(new String[] { "com.websystique.spring.model" });
//        sessionFactory.setHibernateProperties(hibernateProperties());
//        return sessionFactory;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
//        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
//        return dataSource;
//    }

//    private Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
//        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
//        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
//        return properties;
//    }

//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory s) {
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(s);
//        return txManager;
//    }
}
