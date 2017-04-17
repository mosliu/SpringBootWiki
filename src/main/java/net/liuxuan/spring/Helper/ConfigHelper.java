package net.liuxuan.spring.Helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.Helper.ConfigHelper
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/4/14 11:26
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/4/14  |    Moses       |     Created
 */

@Configuration
@Getter
@Setter
public class ConfigHelper {
    @Value("${SprKi.upload.domain}")
    public static String domain;
}
