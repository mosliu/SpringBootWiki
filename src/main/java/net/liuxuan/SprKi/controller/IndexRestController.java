package net.liuxuan.SprKi.controller;

import net.liuxuan.utils.freemarker.FreeMarkerUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.IndexRestController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/15 11:17
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/15 |    Moses       |     Created
 */
@RestController
public class IndexRestController {
    @RequestMapping(value = "/freemarker")
    public String freemarker() {
        FreeMarkerUtil hf = new FreeMarkerUtil();
        try {
            hf.init();
            hf.process(hf);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "down";
    }
}
