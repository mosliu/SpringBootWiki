/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.supportsystem.controller;

import net.liuxuan.supportsystem.entity.NewsPage;
import net.liuxuan.supportsystem.entity.ProjectProgress;
import net.liuxuan.supportsystem.entity.SliderPics;
import net.liuxuan.supportsystem.service.IndexService;
import net.liuxuan.supportsystem.service.SliderPicsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Moses on 2016/2/14.
 */

@Controller
public class IndexController {

    private static Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @Autowired
    private SliderPicsService sliderPicsService;


    @RequestMapping("/")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public String home(HttpServletRequest request,Map<String, Object> model) {
        log.debug("-Access IndexController.home() Method");
//        System.out.println(new File(".").getAbsolutePath());
//        log.debug(SpringContextHelper.getApplicationContext().getApplicationName());

//        try {
//            Html2Pdf.createPdf(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //insert project services
        List<ProjectProgress> progressList = indexService.getProjectProgressList();

        //TODO 需要注意的是新闻现在查询是全部列出，要改！！
        List<NewsPage> newsPageList = indexService.getNewsPageList();

        List<SliderPics> sliderPicsList = sliderPicsService.getAllSliderPics();


        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        model.put("progressList", progressList);
        model.put("newsPageList", newsPageList);
        model.put("sliderPicsList", sliderPicsList);




        return "index";
    }




    @RequestMapping("/ex")
    public String homeex(Map<String, Object> model) throws MissingServletRequestPartException {
        log.debug("-Access IndexController.homeex() Method");


        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        if (1==1) {
            throw new MissingServletRequestPartException("400");
        }
        return "index";
    }

    @RequestMapping(value = "/editor", method = RequestMethod.GET)
//    @Secured("ROLE_USER")
    @PreAuthorize("hasRole('ROLE_USER')")
//    @Secured("${SprKi.editor.acl}")
    //@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public String editor(Map<String, Object> model) {
        log.debug("-Access IndexController.editor() Method");

        model.put("message", "Editor");
        model.put("title", "Hello Editor");
        model.put("date", new Date());
        return "editor";

    }

    @RequestMapping(value = "/editor", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ADMIN')")
//    @Secured("ROLE_USER")
    @PreAuthorize("hasRole('ROLE_USER')")
//    @Secured("${SprKi.editor.acl}")
    public String editorsave(HttpServletRequest request, Map<String, Object> model) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : parameterMap.keySet()) {
            String[] strings = parameterMap.get(s);
            for (int i = 0; i < strings.length; i++) {
                log.info("Parameter key:{} ,values{}:{}", s, i, strings[i]);
            }
        }

        log.debug("-Access IndexController.editor() Post Method");

        model.put("message", "Editor");
        model.put("title", "Hello Editor");
        model.put("date", new Date());
        return "editor";

    }

    @RequestMapping("/jsp")
    public String jsp() {
        return "jsp/aaajsp";
    }

//    @PreAuthorize("#username == principal.username and hasRole('ROLE_USER')")
//    public void changePassword(String username, String password);

//    @RequestMapping(value="json")
//    public ModelAndView json(ModelAndView m,HttpServletRequest request) {
//        MappingJackson2JsonView jsonView = SystemHelper.getWebAppContext(request).getBean(MappingJackson2JsonView.class);
//        m.setView(jsonView);
//        m.getModel().put("name", "javacoder.cn");
////        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        m.getModel().put("list", new ArrayList<String>());
////        model.put("principal", principal);
//        return m;
//    }
//

//    protected Authentication getAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }
//    @ModelAttribute("showLoginLink")
//    public boolean getShowLoginLink() {
//        for (GrantedAuthority authority : getAuthentication().
//                getAuthorities()) {
//            if(authority.getAuthority().equals("ROLE_USER")) {
//                return false;
//            }
//        }
//        return true;
//    }

}
