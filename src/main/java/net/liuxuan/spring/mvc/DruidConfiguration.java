package net.liuxuan.spring.mvc;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.DruidConfiguration
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/12/15 10:17
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/12/15  |    Moses       |     Created
 */
@Configuration
public class DruidConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DruidConfiguration.class);
    @Autowired
    DataSource dataSource;
    @Bean
    public ServletRegistrationBean druidServlet() {
        log.info("init Druid Servlet Configuration ");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("loginUsername", "admin");// 用户名
        initParameters.put("loginPassword", "Moses0319");// 密码
        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
        //initParameters.put("deny", "192.168.20.38");// IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,/ui/smarty/*");
        return filterRegistrationBean;
    }

    //hibernate对于junction的会生成and 1=1 会导致问题。
    //https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter

    @Bean
    public WallFilter WallFilterBean(){

        WallFilter filter = new WallFilter();
        filter.setDbType("mysql");
        WallConfig conf = new WallConfig();
        conf.setConditionAndAlwayTrueAllow(true);
        filter.setConfig(conf);
        //System.out.println(((DruidDataSource)dataSource).dump());

        List<Filter>  proxyFilters =((DruidDataSource)dataSource).getProxyFilters();
        //遍历所有的Filter，找到WallFilter，设置setConditionAndAlwayTrueAllow 选项
        for (int i = 0;i<proxyFilters.size();i++){

            if(proxyFilters.get(i).getClass().getName().equals(com.alibaba.druid.wall.WallFilter.class.getName())){

                ((WallFilter)proxyFilters.get(i)).getConfig().setConditionAndAlwayTrueAllow(true);
            }
        }
        return filter;
    }
}
