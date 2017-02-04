package net.liuxuan.spring.mvc.utilconf.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.MySQL5Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.support.OpenSessionInViewInterceptor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.utilconf.hibernate.HibernateConfiguration
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/18 14:50
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/18 |    Moses       |     Created
 */
@Configuration
//@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {
    private static Logger log =  LoggerFactory.getLogger(HibernateConfiguration.class);
    @Autowired
    private Environment environment;

    @Bean
    public OpenSessionInViewInterceptor openSessionInViewInterceptor(){
        return new OpenSessionInViewInterceptor();
    }

    @Autowired
    private DataSource dataSource;

//    @Autowired
//    private LocalSessionFactoryBean sessionFactory;



    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driverClassName"));
//        dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
//        dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
//        dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
//
//        return dataSource;
//    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();

        properties.put(org.hibernate.cfg.Environment.DIALECT, MySQL5Dialect.class.getName());
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, environment.getRequiredProperty("spring.jpa.properties.hibernate.show_sql"));
        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, environment.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(s);
        return txManager;
    }


//    @Bean
    public Object testBean(PlatformTransactionManager platformTransactionManager) throws SQLException {
        Properties clientInfo = dataSource.getConnection().getClientInfo();
        clientInfo.forEach((o, o2) -> log.debug("key:{} , value:{}",o,o2) );
        log.info("schema:{}",dataSource.getConnection().getSchema());

        log.info(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
        return new Object();
    }
}
