package net.liuxuan.supportsystem.controller.faq;

import net.liuxuan.spring.helper.ResponseHelper;
import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.spring.helper.SystemHelper;
import net.liuxuan.supportsystem.entity.CMSCategoryEditor;
import net.liuxuan.supportsystem.entity.CMSComment;
import net.liuxuan.supportsystem.entity.CMSContentTags;
import net.liuxuan.supportsystem.entity.dto.FAQSearchDTO;
import net.liuxuan.supportsystem.entity.labthink.Department;
import net.liuxuan.supportsystem.entity.labthink.DeviceType;
import net.liuxuan.supportsystem.entity.labthink.Devices;
import net.liuxuan.supportsystem.entity.labthink.FAQContent;
import net.liuxuan.supportsystem.entity.security.DbUser;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.supportsystem.entity.security.Role;
import net.liuxuan.supportsystem.exceptions.ContentNotFoundException;
import net.liuxuan.supportsystem.service.CMSCommentService;
import net.liuxuan.supportsystem.service.CMSContentTagsService;
import net.liuxuan.supportsystem.service.labthink.DepartmentService;
import net.liuxuan.supportsystem.service.labthink.DeviceTypeService;
import net.liuxuan.supportsystem.service.labthink.DevicesService;
import net.liuxuan.supportsystem.service.labthink.FAQContentService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class FAQController {
    private static Logger log = LoggerFactory.getLogger(FAQController.class);

//    @Resource
//    DevicesRepository devicesRepository;

    /**
     * The Devices service.
     */
    @Autowired
    private DevicesService devicesService;

//    @Resource
//    DepartmentRepository departmentRepository;

    /**
     * The Department service.
     */
    @Autowired
    private DepartmentService departmentService;

//    @Resource
//    CMSCategoryRepository cmsCategoryRepository;

    /**
     * The Cms category service.
     */
//    @Autowired
//    private CMSCategoryService cmsCategoryService;


    /**
     * The Device type service.
     */
    @Autowired
    private DeviceTypeService deviceTypeService;


    /**
     * The Faq content service.
     */
    @Autowired
    private FAQContentService faqContentService;

    /**
     * The Cms content tags service.
     */
    @Autowired
    private CMSContentTagsService cmsContentTagsService;

    @Autowired
    private CMSCommentService cmsCommentService;


    /**
     * Gets faq.
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the faq
     */
    @RequestMapping(value = "/faq", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQ(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        log.info("-FAQController.getFAQ() Method");
//        model.put("message", "Editor");
        model.put("title", "FAQ 编辑界面");
        FAQContent faq = new FAQContent();
        faq.setQuestionDate(new Date());
        faq.setDescription("dddddddddddddddd");
        model.put("faq", faq);
//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesRepository.findByDevicenameNotOrderByDevicename(JPAConstants.DELETEDOBJECTSTR);
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "faq/faq_edit";
    }

    /**
     * Gets faq contents.
     *
     * @param id       the id
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the faqid
     */
    @RequestMapping(value = "/faq/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {


        FAQContent faq = faqContentService.findById(id);

        //TODO 无权限时 显示内容
        if (!faqContentService.hasAccessRight(faq)) {
            faq = createNoAccessRightFaqContent("您无权编辑该条信息");
            faq.setId(id);
            model.put("faq", faq);
            return "faq/faq_show";
        }
        if(faq.isDisabled()==true){
            faq = createNoAccessRightFaqContent("该条信息已删除");
            faq.setId(id);
            model.put("faq", faq);
            return "faq/faq_show";
        }


        log.trace("faq to show publish_date is:{}", faq.getPublishDate());
        log.trace("faq to show lastUpdate_date is:{}", faq.getLastUpdateDate());

        model.put("faq", faq);
//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "faq/faq_edit";
    }

    /**
     * Show faqid string.
     *
     * @param id       the id
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the string
     */
    @RequestMapping(value = "/faq/show/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String showFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        FAQContent faq = faqContentService.findById(id);
        if (faq == null) {
            throw new ContentNotFoundException("", id);
        }
        if (!faqContentService.hasAccessRight(faq)) {
            faq = createNoAccessRightFaqContent("您无权查看该条信息");
            faq.setId(id);
            model.put("faq", faq);
            return "faq/faq_show";
        }
        if(faq.isDisabled()==true){
            faq = createNoAccessRightFaqContent("该条信息已删除");
            faq.setId(id);
            model.put("faq", faq);
            return "faq/faq_show";
        }
        boolean isAdmin = SystemHelper.isCurrentUserAdmin();
        //将是否可修改comment设定到每个comment的 canedit属性中（临时属性，不计入数据库的）
        faq.getComments().stream().forEach(e->e.judgeCanEdit(isAdmin));

        model.put("faq", faq);
//        devicesRepository.findAll();
//        List<Devices> devicesAll = devicesRepository.findAll();
//        devicesAll.forEach(devices -> {devices.setDeviceType(null);devices.setDevicename(null);});
//        model.put("devicesAll",devicesAll);
//        UserDetails u = (UserDetails) SystemHelper.getAuthentication().getPrincipal();
        return "faq/faq_show";
    }

    /**
     * Delete faqid string.
     *
     * @param id       the id
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the string
     */
    @RequestMapping(value = "/faq/delete/{id}", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String deleteFAQID(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        FAQContent faq = faqContentService.findById(id);
        if (faq == null) {
            return "redirect:/faq/list";
        }
        if (!faqContentService.hasAccessRight(faq)) {
            faq = createNoAccessRightFaqContent("您无权删除该条信息");
            faq.setId(id);
            model.put("faq", faq);
            return "faq/faq_show";
        }

//        FAQContent faq = faqContentService.findById(id);
        SecurityLogHelper.LogActivity(request, LogActionType.DISABLE, faqContentService.findById(id), "禁用了文章", "/faq/show/" + id);

        faqContentService.disableFAQContentById(id);
        return "redirect:/faq/list";
    }

    /**
     * Create no access right faq content faq content.
     *
     * @param accessErrorText the access error text
     * @return the faq content
     */
    @NotNull
    public FAQContent createNoAccessRightFaqContent(String accessErrorText) {
        FAQContent faq;
        faq = new FAQContent();
        faq.setAnswer(accessErrorText);
        faq.setTitle(accessErrorText);
        faq.setQuestion(accessErrorText);
        faq.setStandard(accessErrorText);
        return faq;
    }

    /**
     * Gets faq list.
     *
     * @param dto      the dto
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the faq list
     */
    @RequestMapping(value = "/faq/list")
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQList(FAQSearchDTO dto, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.debug("===getFAQList logged ,the DTO value is : {}", dto);
//        log.debug("===getFAQList logged ,the isnull is : {}",dto.isAllNull());
        boolean dtoAllNull = dto.isAllNull();
        model.put("dtoNull", dtoAllNull);
        if (dtoAllNull) {
            //参数全为空
        }

        List<Role> currentUserRoles = SystemHelper.getCurrentUserRoles();
        Set<String> rolenames = currentUserRoles.stream().map(e -> e.getRolename()).collect(Collectors.toSet());


        List<FAQContent> allFAQContents = faqContentService.findAllFAQContentsByDto(dto);


        /*
            处理按照权限查询
         */
        List<FAQContent> filteredFAQContents = faqContentService.filterListByAccessRight(allFAQContents,rolenames);
//        List<FAQContent> filteredFAQContents = allFAQContents
//                .stream()
//                .filter(faq -> hasAccessRight(rolenames, faq))
//                .collect(Collectors.toList());
        log.debug("faq list size is {}", filteredFAQContents.size());
        model.put("allfaqlist", filteredFAQContents);
        model.put("dto", dto);
        return "faq/faq_list";
    }


    /**
     * Post faq string.
     *
     * @param faq     the faq
     * @param request the request
     * @param model   the model
     * @return the string
     */
    @RequestMapping(value = "/faq/post", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String postFAQ(FAQContent faq, HttpServletRequest request, Map<String, Object> model) {


//        log.debug("request type is",request.getClass().getCanonicalName());
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("===Parameter key:{} ,values{}:{}", s, i, strings[i]);
            }
        }


        String[] tagStrs = request.getParameterValues("tagStr");

        Set<CMSContentTags> tagsSet = cmsContentTagsService.getTagSetFromTagStrArray(tagStrs);
        faq.setTags(tagsSet);


        String toLog = "新建了文章";
        if (faq.getId() != null) {
            toLog = "更新了文章";
        }

        faqContentService.saveFAQContent(faq);
        //记录活动日志

        SecurityLogHelper.LogActivity(request, LogActionType.CREATE_OR_UPDATE, faq, toLog, "/faq/show/" + faq.getId());

        faq.setDescription("FAQ Commit Finished");
        model.put("faq", faq);
        model.put("title", "FAQ 编辑界面--FAQ提交完成，可继续编辑以更新");
        return "faq/faq_show";

    }

    /**
     * Post comment.
     *
     * @param comment  the comment
     * @param request  the request
     * @param response the response
     * @param model    the model
     */
    @RequestMapping(value = "/faq/comment", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public void postComment(CMSComment comment, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {
//        System.out.println(comment.getId());
        FAQContent faq = faqContentService.findById(comment.getId());
        comment.setContent(faq);
        comment.setId(null);
        comment = cmsCommentService.saveCMSComment(comment);
        ResponseHelper.writeObjectToResponseAsJson(response,comment);
    }
    @RequestMapping(value = "/faq/comment/delete", method = RequestMethod.POST)
    public void deleteComment(CMSComment comment, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {
        System.out.println(comment.getId());
        comment = cmsCommentService.findCMSCommentById(comment.getId());
        faqContentService.refreshCache(comment.getContent().getId());
        boolean b = cmsCommentService.deleteCMSCommentById(comment.getId());
        ResponseHelper.writeObjectToResponseAsJson(response,b);
    }

    /**
     * Gets faq count ajax.
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @throws IOException the io exception
     */
    @RequestMapping(value = "/faq/count_ajax", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public void getFAQCount_ajax(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {

        Map<String, Object> rtnData = new HashMap<String, Object>();
        log.info("-FAQController.getFAQCount() Method");
//        model.put("message", "Editor");
        rtnData.put("title", "FAQ count");

        List l = faqContentService.getFaqGroupByCount();

        l.forEach(item -> {
            Object[] objects = (Object[]) item;
            rtnData.put(((Devices) objects[1]).getDevicename(), objects[0]);
        });
//        for (int i = 0; i < l.size(); i++) {
//
//        }
        rtnData.put("data", l);

        ResponseHelper.writeMapToResponseAsJson(response, rtnData);
    }

    /**
     * Gets faq count.
     *
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @return the faq count
     * @throws IOException the io exception
     */
    @RequestMapping(value = "/faq/count", method = RequestMethod.GET)
//    @PreAuthorize("hasRole('ROLE_USER')")
    public String getFAQCount(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {

        log.info("-FAQController.getFAQCount() Method");
        List faqGroupByCountList = faqContentService.getFaqGroupByCount();
        long total = faqContentService.getFAQContentsCount();
        List<Object[]> faqGroupByAuthorAndDateList = faqContentService.getFaqGroupByAuthorAndDate();

        faqGroupByAuthorAndDateList.stream().forEach(
                e -> {
                    String s = (String) (((Object[]) e)[1]);
                    String s1 = s.substring(4);
                    s1 = s1.length() == 1 ? '0' + s1 : s1;
                    ((Object[]) e)[1] = s.substring(0, 4) + s1;
                }
        );

        faqGroupByAuthorAndDateList.sort(
                (p1, p2) -> (

                        Integer.parseInt((String) (((Object[]) p2)[1])) - Integer.parseInt((String) (((Object[]) p1)[1]))
                )
        );
        Map<String, Map<String, Integer>> AuthorDateCounttempMap = new LinkedHashMap<>();

//        List<LinkedHashMap<String, String>> AuthorCountList = new ArrayList<>();
        for (int i = 0; i < faqGroupByAuthorAndDateList.size(); i++) {
            Object[] objs = (Object[]) faqGroupByAuthorAndDateList.get(i);
//            int nums = ((BigInteger) objs[0]).intValue();
            int nums = ((Number) objs[0]).intValue();
            String date = (String) objs[1];
            String author = ((DbUser) objs[2]).getUsername();
            Map<String, Integer> map = AuthorDateCounttempMap.get(date);
            if (map == null) {
                map = new LinkedHashMap<>();
                map.put("总数", nums);
                map.put(author, nums);
                AuthorDateCounttempMap.put(date, map);
                //不存在
            } else {
                map.replace("总数", map.get("总数") + nums);
                map.put(author, nums);
                //已存在
            }


            LinkedHashMap<String, String> infos = new LinkedHashMap<>();

        }


        model.put("allCount", faqGroupByCountList);
        model.put("allAuthor", AuthorDateCounttempMap);
        model.put("total", total);
        return "faq/faq_count";
    }


    /**
     * Gets faq count ajax.
     *
     * @param keyword  the keyword
     * @param request  the request
     * @param response the response
     * @param model    the model
     * @throws IOException the io exception
     */
    @RequestMapping(value = "/tags/{keyword}", method = RequestMethod.GET)
    public void getTags_ajax(@PathVariable String keyword, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws IOException {
        Map<String, Object> rtnDate = new HashMap<String, Object>();
        log.trace("-FAQController.getTags_ajax() Method");

        List tagl = cmsContentTagsService.getTagByNameLike(keyword);
        rtnDate.put("results", tagl);
        ResponseHelper.writeObjectToResponseAsJson(response, rtnDate);
    }


    /**
     * Deviceslist list.
     *
     * @return the list
     */
    @ModelAttribute("Devices_list")
    public List<Devices> Deviceslist() {
        return devicesService.getAllDevices();
//        return devicesRepository.findByDevicenameNotOrderByDevicename(JPAConstants.DELETEDOBJECTSTR);
    }

    /**
     * Devices typelist list.
     *
     * @return the list
     */
    @ModelAttribute("DevicesType_list")
    public List<DeviceType> DevicesTypelist() {
        return deviceTypeService.getAllDeviceType();
//        return devicesRepository.findByDevicenameNotOrderByDevicename(JPAConstants.DELETEDOBJECTSTR);
    }

    /**
     * Departmentlist list.
     *
     * @return the list
     */
    @ModelAttribute("Department_list")
    public List<Department> Departmentlist() {
        return departmentService.getAllDepartment();
//        return departmentRepository.findBydepartmentNameNotOrderByDepartmentName(JPAConstants.DELETEDOBJECTSTR);
    }
//    @ModelAttribute("tags_list")
//    public List<CMSContentTags> TagsList() {
//        return cmsContentTagsService.getAllCMSContentTags();
////        return departmentRepository.findBydepartmentNameNotOrderByDepartmentName(JPAConstants.DELETEDOBJECTSTR);
//    }

//    @ModelAttribute("Category_list")
//    public List<CMSCategory> Categorylist() {
//        return cmsCategoryService.getAllCMSCategory();
////        return cmsCategoryRepository.findByNameNotOrderByName(JPAConstants.DELETEDOBJECTSTR);
//    }


//    @Autowired
//    CMSCategoryEditor cmsCategoryEditor;

    @Bean
    private CMSCategoryEditor cmsCategoryEditor() {
        return new CMSCategoryEditor();
    }


    /**
     * Init binder.
     *
     * @param binder the binder
     * @throws ServletException the servlet exception
     */
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
