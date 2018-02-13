package net.liuxuan.supportsystem.entity.DTO;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.DTO.BaseDTO
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/28 11:29
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/28  |    Moses       |     Created
 */
@Data
@ToString(includeFieldNames=true)
public class BaseDTO {
    /**
     * The Action.
     */
    public String action;

    /**
     * The ID.
     */
    public String sid;

    /**
     * Get str 2 long id long.
     * if id is wrong,return negative value -1;
     *
     * @return the long
     */
    public Long getStr2LongID(){
        if(NumberUtils.isNumber(sid)){
            return Long.parseLong(sid);
        }
        return -1L;
    }

}
