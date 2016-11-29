package net.liuxuan.SprKi.controller.supporttools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.supporttools.OCRController
 * 功能: OCR功能提供
 * 版本:	@version 1.0
 * 编制日期: 2016/11/29 9:30
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/11/29  |    Moses       |     Created
 */
@Controller
@RequestMapping("/tools")
public class OCRController {
    private static Log logger = LogFactory.getLog(OCRController.class);
    @RequestMapping("/OCR")
    public String getOCR(HttpServletRequest request,
                               HttpServletResponse response, Map<String, Object> model){

        return "tools/ocr";
    }
}
