package net.liuxuan.SprKi.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import net.liuxuan.SprKi.service.security.SecurityUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.SprKi.controller.AuthenticationController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/17 14:38
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/17 |    Moses       |     Created
 */
@Controller
public class AuthenticationController {
    private static final int MAX_NOCAPTCHA_TRIES = 3;
    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    @Resource
    private SecurityUserDetailsService securityUserDetailsService;

    @Autowired
    private Producer captchaProducer = null;

    /**
     * Gets login user.
     *
     * @return the login user
     */
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

//    @ModelAttribute("counter")
//    public AtomicInteger failureCounter() {
//        return new AtomicInteger(0);
//    }

    /**
     * Login model and view.
     *
     * @param model   the model
     * @param session the session
     * @return the model and view
     */
//    public static String encodeBase64(Captcha captcha) {
//        try {
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ImageIO.write(captcha.getImage(), "png", bos);
//            return DatatypeConverter.printBase64Binary(bos.toByteArray());
//        } catch (IOException e) {
//            return "";
//        }
//    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(Map<String, Object> model, HttpSession session) {
        log.debug("===Access AuthenticationController.login() GET Method");
//        AtomicInteger counter = (AtomicInteger) model.get("counter");
//        if (counter.intValue() >= MAX_NOCAPTCHA_TRIES) {
//        Captcha captcha = new Captcha();
//        session.setAttribute("captcha", captcha);
//        model.put("captchaEnc", captcha.getImage());
//        }


        return new ModelAndView("common/login", model);
    }

    /**
     * Login model and view.
     *
     * @param username the username
     * @param password the password
     * @param request  the request
     * @param model    the model
     * @return the model and view
     */
//not work
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String password,
            HttpServletRequest request, Map<String, Object> model) {

        log.debug("Access AuthenticationController.login() Post Method");

//        if (!checkValidateCode(request)) {
//            return new ModelAndView("common/temp", model);
////            return new LoginInfo().failed().msg("验证码错误！");
//        }
//        log.debug("Access AuthenticationController.login() POST Method");
//        username = username.trim();
//
//        UserDetails userDetails = securityUserDetailsService.loadUserByUsername(username);
//
//
//        PreAuthenticatedAuthenticationToken authentication =
//                new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
//
//        //设置authentication中details
//        authentication.setDetails(new WebAuthenticationDetails(request));
//
//        //存放authentication到SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        HttpSession session = request.getSession(true);
//        //在session中存放security context,方便同一个session中控制用户的其他操作
//        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());


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

    /**
     * Logout model and view.
     * 未启用
     * @param model the model
     * @return the model and view
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(Map<String, Object> model) {
//        log.debug("Access AuthenticationController.logout() GET Method");
        return new ModelAndView("common/logout", model);
    }

    /**
     * 验证码判断
     *
     * @param request the request
     * @return boolean
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

    @RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("验证码: " + code);

        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // store the date in the session so that it can be compared
        // against to make sure someone hasn't taken too long to enter
        // their kaptcha
        session.setAttribute(Constants.KAPTCHA_SESSION_CONFIG_DATE, new Date());

        BufferedImage bi = captchaProducer.createImage(capText);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }

    }


}
