package net.liuxuan.SprKi.controller;

import net.liuxuan.SprKi.service.security.SecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.UserController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/17 14:38
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/17 |    Moses       |     Created
 */
@Controller
public class UserController {
    private static Logger log =  LoggerFactory.getLogger(UserController.class);
    @Resource
    private SecurityUserDetailsService securityUserDetailsService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model){
        log.debug("Access UserController.login() GET Method");
        return new ModelAndView("common/login", model);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password,
            HttpServletRequest request, Map<String, Object> model) {

//        log.debug("Access UserController.login() Post Method");

//        if (!checkValidateCode(request)) {
//            return new ModelAndView("common/temp", model);
////            return new LoginInfo().failed().msg("验证码错误！");
//        }
        username = username.trim();

        UserDetails userDetails = securityUserDetailsService.loadUserByUsername(username);


        PreAuthenticatedAuthenticationToken authentication =
                new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        //设置authentication中details
        authentication.setDetails(new WebAuthenticationDetails(request));

        //存放authentication到SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        //在session中存放security context,方便同一个session中控制用户的其他操作
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


        return new ModelAndView("common/temp", model);
//        try {
//            Authentication authentication = myAuthenticationManager.authenticate(authRequest); //调用loadUserByUsername
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            HttpSession session = request.getSession();
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
//            return new LoginInfo().success().msg(authentication.getName());
//        } catch (AuthenticationException ex) {
//            return new LoginInfo().failed().msg("用户名或密码错误");
//        }
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(Map<String, Object> model){
//        log.debug("Access UserController.logout() GET Method");
        return new ModelAndView("common/logout", model);
    }

    /**
     * 验证码判断
     *
     * @param request
     * @return
     */
    protected boolean checkValidateCode(HttpServletRequest request) {
        //TODO  增加验证码
//        String result_verifyCode = request.getSession().getAttribute("verifyResult")
//                .toString(); // 获取存于session的验证值
//        // request.getSession().setAttribute("verifyResult", null);
//        String user_verifyCode = request.getParameter("verifyCode");// 获取用户输入验证码
//        if (null == user_verifyCode || !result_verifyCode.equalsIgnoreCase(user_verifyCode)) {
//            return false;
//        }
        return true;
    }

    public static User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
