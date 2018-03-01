package net.liuxuan.supportsystem.controller.message;

import com.google.gson.Gson;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.supportsystem.entity.Message;
import net.liuxuan.supportsystem.entity.MessageConst;
import net.liuxuan.supportsystem.entity.labthink.TicketContent;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import net.liuxuan.supportsystem.exceptions.ContentNotFoundException;
import net.liuxuan.supportsystem.service.MessageService;
import net.liuxuan.supportsystem.service.labthink.TicketContentService;
import net.liuxuan.supportsystem.service.user.UserDetailInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.faq.FAQController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/3/8 15:52
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/3/8  |    Moses       |     Created
 */

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class MessageController {
    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private TicketContentService ticketContentService;

    @Autowired
    private UserDetailInfoService userDetailInfoService;

    public static String limitContent2WordCount(String content, int count) {
        if (content == null) {
            content = "";
        }
        return content.substring(0, Math.min(900, content.length()));
    }

    //获取发送短消息界面
    @RequestMapping(value = "/msg/new", method = RequestMethod.GET)
    public String getMsg(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        log.info("-MessageController.getMsg() Method");
        Message msg = new Message();


//        model.put("message", "Editor");
        model.put("title", "发送短消息");


        model.put("msg", msg);
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<UserDetailInfo> infoList = userDetailInfoList();
        for (String s : parameterMap.keySet()) {
            if (s.equalsIgnoreCase("username")) {
                UserDetailInfo toUser = userDetailInfoService.findUserDetailInfoByUsername(parameterMap.get(s)[0]);
                msg.setToUser(toUser);
//                infoList.clear();
//                infoList.add(toUser);
                break;
            }
        }
        model.put("userDetailInfoList", infoList);
        return "message/msg_new";
    }

    @RequestMapping(value = "/msg/post", method = RequestMethod.POST)
    public String postMsg(Message message, HttpServletRequest request, Map<String, Object> model) {

//        log.debug("request type is",request.getClass().getCanonicalName());
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("===Parameter key:{} ,values[{}]:{}", s, i, strings[i]);
            }
        }
        if (message == null) {
        } else {
            message.setTitle(limitContent2WordCount(message.getTitle(), 180));
            message.setContent(limitContent2WordCount(message.getContent(), 900));
            message.setComment(limitContent2WordCount(message.getComment(), 180));
            message.setFromUser(SystemHelper.getCurrentUserDetailInfo());
            message.setSendIP(SystemHelper.getCurrentUserIp());
            message.setStatus(MessageConst.MSG_STATUS_SENT);
            message.setMessageType(MessageConst.MSG_TYPE_PRIVATE);
            messageService.sentMessage(message);
            //记录活动日志
            //TODO 不应该记录为Activity
            SecurityLogHelper.LogActivity(request, LogActionType.SEND_MSG, message, "创建了新消息", "/msg/show/" + message.getId());
            model.put("msg", message);
        }
        return "redirect:/msg/list";
    }

    @RequestMapping(value = "/msg/show/{id}", method = RequestMethod.GET)
    public String showMsgID(
            @PathVariable Long id,
            HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> model) {
        Message msg = messageService.findMessageById(id);
        if (msg == null) {
            throw new ContentNotFoundException("Msg Not Found", id);
        }
        UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();
        if (msg.getStatus().equals(MessageConst.MSG_STATUS_SENT)) {
            messageService.markReadMessage(msg);
        }
        if (currentUserDetailInfo.getId() == msg.getToUser().getId() || currentUserDetailInfo.getId() == msg.getFromUser().getId()) {
            model.put("msg", msg);
            return "message/msg_show";
        } else {
            model.put("msg", new Message());
            model.put("title", "不能查看其它人的短消息");
            return "message/msg_show";
        }
    }

    @RequestMapping(value = "/msg/delete/{id}", method = RequestMethod.GET)
    public String deleteMsgID(@PathVariable Long id,
                              HttpServletRequest request,
                              HttpServletResponse response, Map<String, Object> model) {
//        boolean deleteSuccess = messageService.deleteMessageById(id);
         messageService.deleteMessageById(id);
        return "redirect:/msg/list";
    }

    //改造前
    @RequestMapping(value = "/msg/list2", method = RequestMethod.GET)
    public String getMsgList(
            HttpServletRequest request,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            HttpServletResponse response, Map<String, Object> model) {

        log.info("-MessageController.getMsgList() Method");
        model.put("title", "站内信列表");
        List<Message> messageList = messageService.findMessageToUser(SystemHelper.getCurrentUserDetailInfo());
        model.put("messageList", messageList);
        return "message/msg_list";
    }

    @RequestMapping(value = "/msg/list", method = RequestMethod.GET)
    public String getMsgListPageable(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            HttpServletResponse response, Map<String, Object> model) {

        log.info("-MessageController.getMsgList() Method");
        model.put("title", "站内信列表");
        Page<Message> messageList = messageService.findMessageToUserPageable(page, size, SystemHelper.getCurrentUserDetailInfo());
//        model.put("messageList", messageList);
        model.put("datas", messageList);

        List<TicketContent> assignedTo = ticketContentService.findAllTicketContentsAssignedTo(SystemHelper.getCurrentUserDetailInfo(), false);
        model.put("assignedTicket", assignedTo);

        return "message/msg_list";
    }

    @RequestMapping(value = "/msg/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteMsgs(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String ids = request.getParameter("ids");
        if (StringUtils.isBlank(ids)) {
            return "error!";
        } else {
            System.out.println(ids);
        }
        String[] split = ids.split(",");
        Set<Long> collect = Arrays.stream(split).map(s -> Long.parseLong(s)).collect(Collectors.toSet());
        messageService.deleteMessageByIds(collect);
        Gson g = new Gson();
        return g.toJson("ok");
//        return "redirect:/msg/list";
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    public List<UserDetailInfo> userDetailInfoList() {

        return userDetailInfoService.listAllUserDetailInfos();
//        return devicesRepository.findByDevicenameNotOrderByDevicename(JPAConstants.DELETEDOBJECTSTR);
    }


    @InitBinder
    protected void initBinder(
            WebDataBinder binder) throws ServletException {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setLenient(false);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//        binder.registerCustomEditor(CMSCategory.class,cmsCategoryEditor());
//        binder.registerCustomEditor(Department.class,new DepartmentEditor());
    }
}
