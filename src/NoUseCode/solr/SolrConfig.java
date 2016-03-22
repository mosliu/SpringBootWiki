package net.liuxuan.spring.mvc;

import org.apache.solr.client.solrj.SolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.HttpSolrServerFactoryBean;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.SolrConfig
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/21 11:26
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/21  |    Moses       |     Created
 */
@Configuration
//@EnableSolrRepositories(value = "net.liuxuan.SprKi.entity.test.model",multicoreSupport = true)
public class SolrConfig
{
    @Bean
    public SolrServer solrServer() throws Exception
    {
        HttpSolrServerFactoryBean f = new HttpSolrServerFactoryBean();
        f.setUrl("http://localhost:8983/solr");
        f.afterPropertiesSet();
        return f.getSolrServer();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrServer solrServer) throws Exception
    {
        return new SolrTemplate(solrServer());
    }
}
