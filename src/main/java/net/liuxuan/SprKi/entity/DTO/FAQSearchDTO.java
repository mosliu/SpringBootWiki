package net.liuxuan.SprKi.entity.DTO;

import lombok.Data;
import lombok.ToString;
import net.liuxuan.SprKi.entity.CMSCategory;
import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.labthink.Devices;

import java.util.Locale;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.entity.DTO.FAQSearchDTO
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/25 8:22
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/25  |    Moses       |     Created
 */
@Data
@ToString(callSuper=true, includeFieldNames=true)
public class FAQSearchDTO extends BaseSearchDTO {
    public CMSCategory category;
    public Devices devices;
    public Department department;
}
