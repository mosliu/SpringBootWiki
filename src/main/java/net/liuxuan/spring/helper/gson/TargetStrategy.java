package net.liuxuan.spring.helper.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************<br/>
 * 源文件名:  net.liuxuan.spring.Helper.gson.TargetStrategy<br/>
 * 功能: 	用于过滤希望用来转换Gson的方法<br/>
 * 版本:	@version 1.0	  <br/>
 * 编制日期: 2016/3/8 8:02 	<br/>
 * 修改历史: (主要历史变动原因及说明)	<br/>
 * YYYY-MM-DD |    Author      |	 Change Description	<br/>
 * 2016/3/8  |    Moses       |     Created <br/>
 */
public class TargetStrategy implements ExclusionStrategy {
    private static Logger log = LoggerFactory.getLogger(TargetStrategy.class);
    private Class<?> target;
    private String[] fields;
    private Class<?>[] clazz;
    private boolean reverse;

    public TargetStrategy(Class<?> target) {
        super();
        this.target = target;
    }


    @Override
    public boolean shouldSkipClass(Class<?> class1) {
        return false;
    }


    @Override
    public boolean shouldSkipField(FieldAttributes fieldattributes) {
        Class<?> owner = fieldattributes.getDeclaringClass();
        Class<?> c = fieldattributes.getDeclaredClass();
        String f = fieldattributes.getName();
        boolean isSkip = false;
        if (owner == target) {
            if (ArrayUtils.contains(fields, f)) {
                log.debug("fitler field:{} for class:{}", f, owner);
                isSkip = true;
            }
            if (ArrayUtils.contains(clazz, c)) {
                log.debug("fitler class:{} for class:{}", c, owner);
                isSkip = true;
            }
            if (reverse) {
                isSkip = !isSkip;
            }
        }
        return isSkip;
    }


    public void setFields(String[] fields) {
        this.fields = fields;
    }


    public void setClazz(Class<?>[] clazz) {
        this.clazz = clazz;
    }


    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
}
