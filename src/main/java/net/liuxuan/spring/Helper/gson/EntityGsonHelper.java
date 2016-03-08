package net.liuxuan.spring.Helper.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.liuxuan.spring.mvc.utilsinit.hibernate.HibernateProxyTypeAdapter;

import javax.persistence.Entity;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.gson.EntityGsonHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/7 16:57
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/7  |    Moses       |     Created
 */
public class EntityGsonHelper {
    public static GsonBuilder goGsonBuilder(Class<?> a){
//        if(o==null){
//            return new Gson();
//        }
//        Class<?> a = o.getClass();
        GsonBuilder b = new GsonBuilder();
        if(a.isAnnotationPresent(Entity.class)){
            b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
//        }else{
//            return new Gson();
        }
//        Gson gson = b.create();
//        return gson;
        return b;
    }

    public static Gson goEntityGson(Class<?> a){
//        if(o==null){
//            return new Gson();
//        }
//        Class<?> a = o.getClass();
        GsonBuilder b = new GsonBuilder();
        if(a.isAnnotationPresent(Entity.class)){
            b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).setExclusionStrategies(new EntityTargetStrategy());
        }else{
            return new Gson();
        }
        Gson gson = b.create();
        return gson;
//        return b;
    }

}
