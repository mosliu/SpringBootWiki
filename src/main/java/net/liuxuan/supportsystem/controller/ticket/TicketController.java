package net.liuxuan.supportsystem.controller.ticket;

import net.liuxuan.supportsystem.entity.DTO.TicketSearchDTO;
import net.liuxuan.supportsystem.entity.labthink.Department;
import net.liuxuan.supportsystem.entity.labthink.Devices;
import net.liuxuan.supportsystem.entity.labthink.FAQContent;
import net.liuxuan.supportsystem.entity.labthink.TicketContent;
import net.liuxuan.supportsystem.entity.security.DbUser;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.entity.security.Role;
import net.liuxuan.supportsystem.entity.user.UserDetailInfo;
import net.liuxuan.supportsystem.service.CMSCategoryService;
import net.liuxuan.supportsystem.service.labthink.*;
import net.liuxuan.supportsystem.service.user.UserDetailInfoService;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import net.liuxuan.spring.Helper.SystemHelper;
import net.liuxuan.spring.Helper.mail.MailHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

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
public class
TicketController {
    private static Logger log = LoggerFactory.getLogger(TicketController.class);


    @Autowired
    DevicesService devicesService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    CMSCategoryService cmsCategoryService;


    @Autowired
    DeviceTypeService deviceTypeService;
    @Resource
    @Autowired
    UserDetailInfoService userDetailInfoService;
    @Autowired
    TicketContentService ticketContentService;

    @Value("${SprKi.ticket.mailto}")
    String mailto;

    @Value("${SprKi.ticket.enablemail}")
    boolean enablemail;

    @Value("${SprKi.ticket.editRole}")
    String editRole;

    //    @Autowired
//    private MailSender mailSender;
//    @Autowired
//    private MailSender mailSender2;
//    @Autowired
//    private SimpleMailMessage templateMessage;
    @Autowired
    FAQContentService faqContentService;

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public String getTicket(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.info("TicketController.getTicket() method invoked");

        model.put("title", "咨询问题提交界面");
        TicketContent ticket = new TicketContent();
        ticket.setDescription("Tickets");
        ticket.setSubmitDate(new Date());
        model.put("ticket", ticket);
//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesService.getAllDevices();


//
//        UserDetails userDetails = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
//        Users u = usersRepository.findOne(userDetails.getUsername());
//        UserDetailInfo udi = userDetailInfoRepository.findByDbUser(u);
//
//
//        if (udi == null) {
//            System.out.println("+++++++++++++++++++++udi is null!!!!!!!-!!!!!");
//        } else {
//            System.out.println("+++++++++++++++++++++email is: " + udi.getEmail());
//        }
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "ticket/ticket_post";
    }

    @RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        TicketContent content = ticketContentService.findById(id);
        model.put("ticket", content);

//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();

        DbUser u = SystemHelper.getCurrentUser();
        if (u.getUsername().equals(content.getAuthor().getUsername()) || u.getUsername().equals(content.getLastUpdateUser().getUsername())) {
            return "ticket/ticket_post";
        }
        List<Role> currentUserRoles = SystemHelper.getCurrentUserRoles();
//        boolean anyMatchFAQ = currentUserRoles.stream().anyMatch(e -> e.getRolename().equals("ROLE_FAQ"));
        boolean anyMatchFAQ = currentUserRoles.stream().anyMatch(e -> e.getRolename().equals(editRole));
        if(anyMatchFAQ){
            return "ticket/ticket_post";
        }

        return "redirect:/ticket/show/" + id;
    }

    @RequestMapping(value = "/ticket/show/{id}", method = RequestMethod.GET)
    public String getTicketID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        TicketContent content = ticketContentService.findById(id);
//        log.trace("Ticket to show publish_date is:{}", content.getPublishDate());
//        log.trace("Ticket to show lastUpdate_date is:{}", content.getLastUpdateDate());

        model.put("ticket", content);
//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "ticket/ticket_show";
    }

    @RequestMapping(value = "/ticket/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteTicketID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

//        FAQContent faq = faqContentService.findById(id);
        ticketContentService.deleteTicketContentById(id);
        return "redirect:/ticket/list";
    }

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String postTicket(TicketContent ticket, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

//        Map<String, String[]> parameterMap = request.getParameterMap();
//        for (String s : parameterMap.keySet()) {
//            String[] strings = parameterMap.get(s);
//            for (int i = 0; i < strings.length; i++) {
//                log.info("===Parameter key:{} ,values{}:{}", s, i, strings[i]);
//            }
//        }


        log.info("TicketController.postTicket() method invoked");


        UserDetails userDetails = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
//        DbUser u = usersRepository.findOne(userDetails.getUsername());
//        UserDetailInfo udi = userDetailInfoRepository.findByDbUser(u);
        UserDetailInfo udi = userDetailInfoService.findUserDetailInfoByUsername(userDetails.getUsername());


        model.put("title", "问题提交完成，我们将尽快对问题跟进整理！");

        if (enablemail) {
            Map<String, Object> variables = new HashMap<String, Object>();

            variables.put("name", userDetails.getUsername());
            variables.put("submitDate", new Date());
            variables.put("submitTitle", ticket.getTitle());
            variables.put("submitContent", ticket.getQuestion());
//        variables.put("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
            List<Map<String, String>> imageList = new ArrayList<>();

            Map<String, String> image1 = new HashMap<>();
            image1.put("imageResourceName", "pic_logo");
            image1.put("imageContentType", "image/png");
            image1.put("imagePath", "static/officiallogo.png");
            imageList.add(image1);
//        variables.put("imageResourceName", "aa"); // so that we can reference it from HTML
            variables.put("imageList", imageList); // so that we can reference it from HTML

            try {
//                MailHelper.SendMail(mailto, "New Question:"+ticket.getTitle(), variables, "mail/mailTicketSubmit");
                String email = ticket.getAssignToUser().getEmail();
                email= email==null?mailto:email;

                MailHelper.SendMail(ticket.getAssignToUser().getEmail(), "New Question:"+ticket.getTitle(), variables, "mail/mailTicketSubmit");
//                if (udi != null) {
//                    MailHelper.SendMail(udi.getEmail(), "subject aa", variables, "mail/mailTicketSubmit");
//                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        ticket.setDescription("提交问题");
        model.put("ticket", ticket);
//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesRepository.findAll();


        String toLog = "新建了文章";
        if (ticket.getId() != null) {
            toLog = "更新了文章";
        }

//        //提交时间改为设置时间
//        ticket.setSubmitDate(new Date());
        ticketContentService.saveTicketContent(ticket);

        SecurityLogHelper.LogActivity(request, LogActionType.CREATE_OR_UPDATE, ticket, toLog, "/ticket/show/" + ticket.getId());

//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "ticket/ticket_post";
    }

    /**
     * 打开回答页面。
     *
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "ticket/answer/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String AnswerTicket(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        TicketContent content = ticketContentService.findById(id);
        FAQContent faq = new FAQContent();
        BeanUtils.copyProperties(content, faq);
//        faq.setId(null); //删除复制的id 防止出错
        faq.setQuestionDate(content.getSubmitDate());
        model.put("faq", faq);
        model.put("DevicesType_list", deviceTypeService.getAllDeviceType());
        model.put("ticketId", id);
//        model.addAttribute("faq", faq);
//        model.addAttribute("ticketId", id);
        if (content.getFaq() != null) {
            return "redirect:/faq/show/" + content.getFaq().getId();
        }
        return "ticket/ticket_answer";
    }

    /**
     * 提交回答
     *
     * @param faq
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "ticket/answer", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String AnswerTicket(FAQContent faq, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        long ticketid = faq.getId();
        System.out.println("ticketid:" + ticketid);
        faq.setId(null);


        faq = faqContentService.saveFAQContent(faq);
        System.out.println("faqid:" + faq.getId());
        model.put("faq", faq);

        TicketContent content = ticketContentService.findById(ticketid);
        content.setFaq(faq);
        content.setResolved(true);
        content.setResolvedDate(new Date());
        UserDetailInfo currentUserDetailInfo = SystemHelper.getCurrentUserDetailInfo();
        content.setAnswerUser(currentUserDetailInfo);
        ticketContentService.saveTicketContent(content);

        SecurityLogHelper.LogActivity(request, LogActionType.CREATE_OR_UPDATE, faq, "解决了问题", "/ticket/show/" + ticketid);


        return "faq/faq_show";
    }


    @RequestMapping(value = "/ticket/list")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQList(TicketSearchDTO dto, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.debug("===getTicketList logged ,the DTO value is : {}", dto);
//        log.debug("===getFAQList logged ,the isnull is : {}",dto.isAllNull());
        boolean dtoAllNull = dto.isAllNull();
        model.put("dtoNull", dtoAllNull);
        if (dtoAllNull) {
            //参数全为空
        }


        List<TicketContent> allContents = ticketContentService.findAllTicketContentsByDto(dto);
        log.info("list size is {}", allContents.size());
        model.put("alllist", allContents);
        model.put("dto", dto);
        return "ticket/ticket_list";
    }


//    @RequestMapping(value = "/testmail", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public String testMail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
//        log.info("TicketController.testMail() method invoked");
//
//
//        // Create a thread safe "copy" of the template message and customize it
//        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
//        msg.setTo("liuxuan@labthink.com");
//        msg.setText(
//                "Dear A"
//                        + ", thank you for placing order. Your order number is "
//                        + "3");
//        try {
//
//            this.mailSender.send(msg);
//        } catch (MailException ex) {
//            // simply log it and go on...
//            System.err.println(ex.getMessage());
//        }
//        return "index";
//    }

    @RequestMapping(value = "/testmail3", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String testMail3(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.info("TicketController.testMail3() method invoked");
        Map<String, Object> variables = new HashMap<String, Object>();

        variables.put("name", "name");
        variables.put("subscriptionDate", new Date());
        variables.put("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
        List<Map<String, String>> imageList = new ArrayList<>();

        Map<String, String> image1 = new HashMap<>();
        image1.put("imageResourceName", "aa");
        image1.put("imageContentType", "image/png");
        image1.put("imagePath", "static/officiallogo.png");
//        variables.put("imageResourceName", "aa"); // so that we can reference it from HTML
        variables.put("imageList", imageList); // so that we can reference it from HTML


        try {
            MailHelper.SendMail("830319@qq.com", "subject aa", variables, "mail/mailTicketSubmit");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return "index";
    }

//    @RequestMapping(value = "/testmail2", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public String testMail2(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
//        log.info("TicketController.testMail2() method invoked");
//
//
//        // Create a thread safe "copy" of the template message and customize it
//        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
//        msg.setTo("830319@qq.com");
//        msg.setText(
//                "Dear A"
//                        + ", thank you for placing order. Your order number is "
//                        + "3");
//        try {
//            this.mailSender.send(msg);
//        } catch (MailException ex) {
//            // simply log it and go on...
//            System.err.println(ex.getMessage());
//        }
//        return "index";
//    }


    @ModelAttribute("userDetailInfoList")
    public List<UserDetailInfo> userDetailInfoList() {
        return userDetailInfoService.listAllUserDetailInfos();
//        return devicesRepository.findByDevicenameNotOrderByDevicename(JPAConstants.DELETEDOBJECTSTR);
    }

    @ModelAttribute("Devices_list")
    public List<Devices> Deviceslist() {
        return devicesService.getAllDevices();
    }

    @ModelAttribute("Department_list")
    public List<Department> Departmentlist() {
        return departmentService.getAllDepartment();
//        return departmentRepository.findBydepartmentNameNotOrderByDepartmentName(JPAConstants.DELETEDOBJECTSTR);
    }

//    @ModelAttribute("Category_list")
//    public List<CMSCategory> Categorylist() {
//        return cmsCategoryService.getAllCMSCategory();
//    }

    //    @ModelAttribute("DevicesType_list")
//    public List<DeviceType> DevicesTypelist() {
//        return deviceTypeService.getAllDeviceType();
////        return devicesRepository.findByDevicenameNotOrderByDevicename(JPAConstants.DELETEDOBJECTSTR);
//    }
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
