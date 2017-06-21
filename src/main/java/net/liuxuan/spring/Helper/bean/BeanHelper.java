package net.liuxuan.spring.Helper.bean;

import java.lang.reflect.InvocationTargetException;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.BeanHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/24 9:00
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/24  |    Moses       |     Created
 */
public class BeanHelper  {

    /**
     * Copy from bean to bean,which origin89
     *
     * @param dest the valueObject,the Persist
     * @param origin the postObject,the origin
     */
    public static void copyWhenDestFieldNull(Object dest, Object origin) throws InvocationTargetException, IllegalAccessException {
        CopyWhenDestFieldNullBeanUtilsBean s = new CopyWhenDestFieldNullBeanUtilsBean();
        s.copyProperties(dest, origin);
    }


    /**
     * Copy from bean to bean,which origin89
     *
     * @param dest the valueObject,the Persist
     * @param origin the postObject,the origin
     */
    public static void CopyWhenSrcFieldNotNullBeanUtilsBean(Object dest, Object origin) throws InvocationTargetException, IllegalAccessException {
        CopyWhenSrcFieldNotNullBeanUtilsBean s = new CopyWhenSrcFieldNotNullBeanUtilsBean();
        s.copyProperties(dest, origin);
    }




}
