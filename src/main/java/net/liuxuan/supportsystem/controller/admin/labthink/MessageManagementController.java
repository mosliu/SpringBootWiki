package net.liuxuan.supportsystem.controller.admin.labthink;

import net.liuxuan.spring.helper.ResponseHelper;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.supportsystem.entity.Message;
import net.liuxuan.supportsystem.entity.dto.BaseDTO;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.admin.labthink.MessageRepository
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/04/22 09:48
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017-04-22  |    Moses        |     Created
 */

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MessageManagementController {
    private static Logger log = LoggerFactory.getLogger(MessageManagementController.class);

    @Autowired
    MessageService messageService;

    @RequestMapping("messageManage")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getPages(Map<String, Object> model) {
        Message msg = new Message();
        model.put("msg",msg);

        return "admin/" + "messageManage" + " :: middle";

    }

    @RequestMapping("message")
    public String messageManage(@ModelAttribute("dto") BaseDTO dto, HttpServletRequest request,
                                HttpServletResponse response, Map<String, Object> model) throws IOException {
        log.info("===messageManage logged ,the _dto value is : {}", dto.toString());

        switch (dto.action) {
            case "edit":
                Message message;
                Long id = dto.getStr2LongID();

                message = messageService.findMessageById(id);
                if (message != null) {
                } else {
                    throw new IOException("Got Wrong ID");
                }
                model.put("message", message);
                return "admin/snipplets/div_message :: messageedit";
            default:
                return "redirect:/admin/message_ajax";
//                break;
        }
    }


    @RequestMapping("message_ajax")
//    @ResponseBody
    public void messageManageAjax(@ModelAttribute("dto") BaseDTO _dto, Message _message, HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");
        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("===messageManageAjax logged ,the value is : {}", _dto.toString());
//        Long id = _dto.getStr2LongID();

//        response.setContentType("application/json");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        switch (_dto.action) {
            case "add":
                String title = _message.getTitle();
                String content = _message.getContent();
                messageService.announceMessage(title,content);
                rtnData.put("status", "success");
                rtnData.put("msg", "成功发布公告");
                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_CREATE, _message, "发布公告", "");
//                messageService.saveMessage(_message);
                break;
//            case "delete":
//                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_DELETE, _dto, "删除角色", "");
//                boolean b = messageService.deleteMessageById(id);
//                if (b) {
//                    rtnData.put("status", "success");
//                    rtnData.put("msg", "成功删除Message");
//                } else {
//                    rtnData.put("error", "ERROR_MessageNotExists");
//                    rtnData.put("status", "fail");
//                    rtnData.put("msg", "Message不存在，删除失败");
//                }
//                break;
//            case "update":
//                messageService.saveMessage(_message);
//                SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN_UPDATE, _message, "更新Message", "");
//                rtnData.put("success1", "success!");
//                break;
            default:

                break;
        }
//        return "";
//        mapper.writeValue(response.getWriter(), rtnData);
        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }


    @ModelAttribute("Message_list")
    public List<Message> Messagelist() {
        return messageService.getAllMessage();
    }
}