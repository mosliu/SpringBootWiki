package net.liuxuan.spring.cache.ehcache;

//import org.ehcache.event.CacheEvent;
//import org.ehcache.event.CacheEventListener;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.cache.ehcache.EventLogger
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/4/14 15:19
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/4/14  |    Moses       |     Created
 */
public class EventLogger implements CacheEventListener {

    private static Logger log = LoggerFactory.getLogger(EventLogger.class);

//    @Override
//    public void onEvent(CacheEvent<?, ?> cacheEvent) {
//        log.trace("Event: " + cacheEvent.getType() + " Key: " + cacheEvent.getKey() + " old value: " + cacheEvent.getOldValue() + " new value: " + cacheEvent.getNewValue());
//    }

    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {
        log.trace("Ehcache Removed:{},{}",ehcache,element);
    }

    @Override
    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
        log.trace("Ehcache Put:{},{}",ehcache,element);
    }

    @Override
    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {
        log.trace("Ehcache Updated:{},{}",ehcache,element);
    }

    @Override
    public void notifyElementExpired(Ehcache ehcache, Element element) {
        log.trace("Ehcache Expired:{},{}",ehcache,element);
    }

    @Override
    public void notifyElementEvicted(Ehcache ehcache, Element element) {
        log.trace("Ehcache Evicted:{},{}",ehcache,element);
    }

    @Override
    public void notifyRemoveAll(Ehcache ehcache) {
        log.trace("Ehcache removeAll:{}",ehcache);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }


    /**
     *
     */
    @Override
    public void dispose() {

    }
}

