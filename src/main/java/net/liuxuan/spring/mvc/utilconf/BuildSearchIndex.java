package net.liuxuan.spring.mvc.utilconf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.utilconf.BuildSearchIndex
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/12 8:47
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/12  |    Moses       |     Created
 */
@Component
public class BuildSearchIndex implements ApplicationListener {
    private static Logger log =  LoggerFactory.getLogger(BuildSearchIndex.class);

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Value("${SprKi.lucene.init}")
    private String init;

    private static int count = 0;
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(!init.equalsIgnoreCase("true")){
//        if(!init){
            return;
        }
        if(count>0){
            return;
        }
        count++;
        try {
            FullTextEntityManager fullTextEntityManager =
                    Search.getFullTextEntityManager(entityManagerFactory.getNativeEntityManagerFactory().createEntityManager());
//                    Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        }
        catch (InterruptedException e) {
//            e.printStackTrace();
            log.error("An error occurred trying to build the serach index! ",e);
        }
        return;
    }
}
