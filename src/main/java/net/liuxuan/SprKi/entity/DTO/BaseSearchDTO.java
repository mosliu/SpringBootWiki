package net.liuxuan.SprKi.entity.DTO;

import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.DTO.BaseSearchDTO
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/25 8:19
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/25  |    Moses       |     Created
 */
@Data
@ToString(callSuper=true, includeFieldNames=true)
public class BaseSearchDTO extends BaseDTO{

    /**
     * The Id.
     */
    public Long id;

    /**
     * The From date.起始日期
     */
    public Date fromDate;

    /**
     * The To date.截至日期
     */
    public Date toDate;

    /**
     * The Keyword. 关键字
     */
    public String keyword;

    /**
     * The User.用户
     */
    public String user;

    /**
     * The Limit. 查询限制
     */
    public Integer limit;

    /**
     * 是否禁用
     */
    public boolean disabled;
    /**
     * Is all null boolean.
     *
     * @return the boolean
     */
    public boolean isAllNull(){
        Field[] fields = this.getClass().getFields();
        boolean rtn = true;
//        log.debug("===isAllNull logged ,the fields num is : {}",fields.length);
        for (int i = 0; i < fields.length; i++) {
            boolean tempbo = false;
            try {
                tempbo =  (fields[i].get(this)== null);
//                log.debug("===isAllNull logged ,the value {} is null?: {}", fields[i].getName(),tempbo) ;
                rtn = rtn && tempbo;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return rtn;
    }


}
