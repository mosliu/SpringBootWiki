package net.liuxuan.supportsystem.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.liuxuan.supportsystem.entity.labthink.FAQContent;
import net.liuxuan.supportsystem.entity.labthink.TicketContent;
import net.liuxuan.supportsystem.entity.security.SecurityLog;
import net.liuxuan.supportsystem.service.security.SecurityLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.ActivityController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/2/20 8:45
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/2/20  |    Moses       |     Created
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
    private static Logger log = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    SecurityLogService securityLogService;

    @RequestMapping(value = "/last10")
    @ResponseBody
    public List getLastestTenActivities() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        List<SecurityLog> activities = securityLogService.getLastestTenActivities(0);
        List<HashMap<String, String>> rtn = new ArrayList();
        activities.forEach(node -> {
            if (node.getLogInfoType().equals(FAQContent.class.getName())
                            || node.getLogInfoType().equals(TicketContent.class.getName())) {

                HashMap<String, String> map = new HashMap();
                map.put("action", node.getAction());
                map.put("username", node.getUsername());
                map.put("url", node.getUrl());
                map.put("operation", node.getOperation());
                String jsonstr = node.getLogInfo();
                ObjectMapper jsonMapper = new ObjectMapper();
                try {
                    JsonNode jsonNode = null;
                    jsonNode = jsonMapper.readTree(jsonstr);
                    map.put("title", jsonNode.get("title").textValue());
                } catch (IOException e) {
                    log.error("Json 处理出错", e);
//                    e.printStackTrace();
                }
                rtn.add(map);
            }
        });

        return rtn;
    }
}
