package net.liuxuan.spring.cache.ehcache;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;

import java.util.Properties;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.cache.ehcache.EhcacheEventLoggerListenerFactory
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/4/19 8:23
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/4/19  |    Moses       |     Created
 */
public class EhcacheEventLoggerListenerFactory extends CacheEventListenerFactory {

    @Override
    public CacheEventListener createCacheEventListener(Properties properties) {
        return new EventLogger();
    }
}
