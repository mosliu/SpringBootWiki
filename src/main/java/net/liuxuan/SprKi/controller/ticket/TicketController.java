package net.liuxuan.SprKi.controller.ticket;

import net.liuxuan.SprKi.entity.CMSCategory;
import net.liuxuan.SprKi.entity.DTO.TicketSearchDTO;
import net.liuxuan.SprKi.entity.labthink.Department;
import net.liuxuan.SprKi.entity.labthink.Devices;
import net.liuxuan.SprKi.entity.labthink.FAQContent;
import net.liuxuan.SprKi.entity.labthink.TicketContent;
import net.liuxuan.SprKi.entity.security.Users;
import net.liuxuan.SprKi.entity.user.UserDetailInfo;
import net.liuxuan.SprKi.repository.CMSCategoryRepository;
import net.liuxuan.SprKi.repository.labthink.DepartmentRepository;
import net.liuxuan.SprKi.repository.labthink.DevicesRepository;
import net.liuxuan.SprKi.repository.security.UsersRepository;
import net.liuxuan.SprKi.repository.user.UserDetailInfoRepository;
import net.liuxuan.SprKi.service.labthink.TicketContentService;
import net.liuxuan.spring.Helper.SystemHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.ticket.TicketController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/8/15 11:30
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/8/15  |    Moses       |     Created
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class TicketController {
    private static Logger log = LoggerFactory.getLogger(TicketController.class);

    @Resource
    DevicesRepository devicesRepository;



    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage templateMessage;

    @Resource
    DepartmentRepository departmentRepository;

    @Resource
    CMSCategoryRepository cmsCategoryRepository;

    @Resource
    UsersRepository usersRepository;

    @Resource
    UserDetailInfoRepository userDetailInfoRepository;

    @Autowired
    TicketContentService ticketContentService;



    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getTicket(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.info("TicketController.getTicket() method invoked");


        model.put("title", "咨询问题提交界面");
        TicketContent ticket = new TicketContent();
        ticket.setDescription("dddddddddddddddd");
        model.put("ticket", ticket);
//        devicesRepository.findAll();
        List<Devices> devicesAll = devicesRepository.findAll();
        UserDetails userDetails = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        Users u = usersRepository.findOne(userDetails.getUsername());
        UserDetailInfo udi =userDetailInfoRepository.findByUsers(u);
        if(udi==null){
            System.out.println("+++++++++++++++++++++udi is null!!!!!!!-!!!!!");
        }else {
            System.out.println("+++++++++++++++++++++email is: " + udi.getEmail());
        }
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "ticket/ticket_post";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String postTicket(TicketContent ticket,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("===Parameter key:{} ,values{}:{}",s,i,strings[i]);
            }
        }



        log.info("TicketController.postTicket() method invoked");


        model.put("title", "问题提交完成，我们将尽快对问题跟进整理！");
        ticket.setDescription("提交问题");
        model.put("ticket",ticket);
//        devicesRepository.findAll();
        List<Devices> devicesAll = devicesRepository.findAll();
        UserDetails userDetails = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        Users u = usersRepository.findOne(userDetails.getUsername());
        UserDetailInfo udi =userDetailInfoRepository.findByUsers(u);
        if(udi==null){
            System.out.println("+++++++++++++++++++++udi is null!!!!!!!-!!!!!");
        }else {
            System.out.println("+++++++++++++++++++++email is: " + udi.getEmail());
        }


        ticketContentService.saveTicketContent(ticket);

//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "ticket/ticket_post";
    }

    @RequestMapping(value = "/ticketlist")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQList(TicketSearchDTO dto, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model)  {
        log.debug("===getTicketList logged ,the DTO value is : {}",dto);
//        log.debug("===getFAQList logged ,the isnull is : {}",dto.isAllNull());
        boolean dtoAllNull = dto.isAllNull();
        model.put("dtoNull", dtoAllNull);
        if(dtoAllNull){
            //参数全为空
        }

        List<TicketContent> allContents = ticketContentService.findAllTicketContentsByDto(dto);
        log.info("list size is {}",allContents.size());
        model.put("alllist",allContents);
        model.put("dto",dto);
        return "ticket/ticket_list";
    }









    @RequestMapping(value = "/testmail", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String testMail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.info("TicketController.testMail() method invoked");


        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo("830319@qq.com");
        msg.setText(
                "Dear A"
                        + ", thank you for placing order. Your order number is "
                        + "3");
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
        return "index";
    }

    @ModelAttribute("Devices_list")
    public List<Devices> Deviceslist() {
        return devicesRepository.findAll();
    }
    @ModelAttribute("Department_list")
    public List<Department> Departmentlist() {
        return departmentRepository.findAll();
    }
    @ModelAttribute("Category_list")
    public List<CMSCategory> Categorylist() {
        return cmsCategoryRepository.findAll();
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
