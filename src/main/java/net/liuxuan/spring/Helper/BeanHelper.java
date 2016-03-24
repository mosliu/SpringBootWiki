package net.liuxuan.spring.Helper;

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
     * Copy from po 2 vo.
     *
     * @param VO the valueObject,the dest
     * @param PO the postObject,the origin
     */
    public static void copyWhenNull(Object VO,Object PO) throws InvocationTargetException, IllegalAccessException {
        CopyWhenNullBeanUtilsBean s = new CopyWhenNullBeanUtilsBean();
        s.copyProperties(VO, PO);
    }


}
