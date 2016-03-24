/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.SprKi.controller;

import com.baidu.ueditor.ActionEnter;
import com.google.gson.Gson;
import net.liuxuan.spring.Helper.SystemHelper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moses on 2016/2/14.
 */

@Controller
public class IndexController {

    private static Logger log =  LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public String home(Map<String, Object> model) {
        log.debug("-Access IndexController.home() Method");

        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "index";
    }
    @RequestMapping("/ex")
    public String homeex(Map<String, Object> model) throws MissingServletRequestPartException {
        log.debug("-Access IndexController.homeex() Method");



        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        if(true) {
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
                log.info("Parameter key:{} ,values{}:{}",s,i,strings[i]);
            }
        }

        log.debug("-Access IndexController.editor() Post Method");

        model.put("message", "Editor");
        model.put("title", "Hello Editor");
        model.put("date", new Date());
        return "editor";

    }

    @RequestMapping("/jsp")
    public String jsp(){
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
