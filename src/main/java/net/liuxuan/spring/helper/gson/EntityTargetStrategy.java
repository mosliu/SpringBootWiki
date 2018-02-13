package net.liuxuan.spring.helper.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

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
public class EntityTargetStrategy implements ExclusionStrategy {
    private static Logger log = LoggerFactory.getLogger(EntityTargetStrategy.class);

    @Override
    public boolean shouldSkipClass(Class<?> class1) {
        return false;
    }


    @Override
    public boolean shouldSkipField(FieldAttributes fieldattributes) {
//        Class<?> owner = fieldattributes.getDeclaringClass();
        Class<?> c = fieldattributes.getDeclaredClass();
//        String f = fieldattributes.getName();
//        String typename = fieldattributes.getDeclaredType().getTypeName();
//        boolean isSkip = false;

//        log.debug("filtered field:{} filedClass:{} typename:{}", f, c.getName(),typename);
//        log.debug("filtered field:{}.isInstance(int.class)  :{}",f, c.isInstance(int.class));
//        log.debug("filtered field:{}.isInstance(String.class)  :{}",f, c.isInstance(String.class));

        if(c.equals(List.class)||c.equals(Set.class)){
//            log.debug("return true");
            return true;
        }
//        return isSkip;
        return false;
    }


}
