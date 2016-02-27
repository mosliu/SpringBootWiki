package net.liuxuan.SprKi.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.mvc.mvcErrorController
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/15 11:04
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/15 |    Moses       |     Created
 */

//注意使用注解@ControllerAdvice作用域是全局Controller范围
 //可应用到所有@RequestMapping类或方法上的@ExceptionHandler、@InitBinder、@ModelAttribute，在这里是@ExceptionHandler
@Controller
@ControllerAdvice
public class ErrorHandleController implements ErrorController {
//public class ErrorHandleController  {

    private static final String ERROR_PATH = "/error";


    @RequestMapping(value = ERROR_PATH)
    public ModelAndView handleError(HttpServletRequest request,
                              HttpServletResponse response, Object handler, Exception ex) {
//        error.hasErrors();
        ModelAndView model = new ModelAndView("common/temp");
        model.getModel().put("status",response.getStatus());
        model.getModel().put("error",ex.getMessage());
        model.getModel().put("message", ex.getMessage());
        model.getModel().put("title", ex.getMessage());
        model.getModel().put("date", new Date());
//        return "common/temp";
        return model;
    }

    /**
     * 处理碰到的找不到的页面，提示404错误
     * @param model
     * @return
     */
    @RequestMapping("*")
    public String handle404(Map<String, Object> model) {
//        error.hasErrors();
        model.put("message", "ERROR MSG");
        model.put("title", "ERROR_404");
        model.put("date", new Date());
        return "common/404";
    }

    @RequestMapping(value = "/invalid")
    public String handleInvalid(Map<String, Object> model) {

        model.put("message", "INVALID MSG");
        model.put("title", "INVALID");
        model.put("date", new Date());
        return "common/invalid";
    }


    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView model = new ModelAndView("common/temp");
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("title", "Error Happened");
        model.getModel().put("message", "Error Happened");
        model.getModel().put("date", new Date());
        model.addObject("errMsg", "this is Exception.class");
        ex.printStackTrace();
        return model;

    }


    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedExceptionException(Exception ex) {
        ModelAndView model = new ModelAndView("common/accessdenied");
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("title", "Access Denied");
        model.getModel().put("message", "AccessDenied");
        model.getModel().put("date", new Date());

        return model;

    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleMethodNotSupportedException(Exception ex) {
        ModelAndView model = new ModelAndView("common/temp");
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("title", "MethodNotSupported");
        model.getModel().put("message", "MethodNotSupported");
        model.getModel().put("date", new Date());
        model.getModel().put("status","405");

        return model;

    }


//    @ExceptionHandler
//    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.BAD_REQUEST.value());
//    }
//
//    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
//    void handleBadRequests(HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.BAD_REQUEST.value());
//    }


}
