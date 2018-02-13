package net.liuxuan.supportsystem.controller.supporttools;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.supporttools.FormulaComputeController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/10/28 16:29
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/10/28  |    Moses       |     Created
 */
@Controller
@RequestMapping("/tools")
public class FormulaComputeController {
//    @RequestMapping("/formula/vacuum")
//    public String getVacuum(HttpServletRequest request,
//                               HttpServletResponse response, Map<String, Object> model){
//        return "tools/vacuum";
//    }
//    @RequestMapping("/formula/weight")
//    public String getWeight(HttpServletRequest request,
//                            HttpServletResponse response, Map<String, Object> model){
//        return "tools/weight";
//    }
    // formula/vacuum
    // formula/weight
    @RequestMapping("/formula/{address}")
    public String getXXX(@PathVariable("address") String address, HttpServletRequest request,
                         HttpServletResponse response, Map<String, Object> model){
        return "tools/"+address;
    }
}
