package net.liuxuan.supportsystem.controller;

import net.liuxuan.spring.helper.SecurityLogHelper;
import net.liuxuan.supportsystem.entity.security.LogActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
//@Controller
@ControllerAdvice
public class ErrorHandleController {
//public class ErrorHandleController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private static Logger log = LoggerFactory.getLogger(ErrorHandleController.class);

    /**
     * Handle error model and view.
     *
     * @param request  the request
     * @param response the response
     * @param handler  the handler
     * @param ex       the ex
     * @return the model and view
     */
//    @RequestMapping(value = ERROR_PATH)
    public ModelAndView handleError(HttpServletRequest request,
                                    HttpServletResponse response, Object handler, Exception ex) {
//        error.hasErrors();
        log.error("ERROR Happend! handleError() invoked!");
        ModelAndView model = new ModelAndView("common/invalid");
        model.getModel().put("status", response.getStatus());
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("message", ex.getMessage());
        model.getModel().put("title", ex.getMessage());
        model.getModel().put("date", new Date());
//        return "common/temp";
        return model;
    }

    /**
     * 处理碰到的找不到的页面，提示404错误
     *
     * @param model the model
     * @return string
     */
//    @RequestMapping("*")
//    public String handle404(Map<String, Object> model) {
////        error.hasErrors();
//        model.put("message", "ERROR MSG");
//        model.put("title", "ERROR_404");
//        model.put("date", new Date());
//        return "common/404";
//    }

    //    @RequestMapping(value = "/invalid")
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
    public String getErrorPath() {
        return ERROR_PATH;
    }

    /**
     * Handle all exception model and view.
     *
     * @param req the req
     * @param ex  the ex
     * @return the model and view
     * @throws Exception the exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(HttpServletRequest req, Exception ex) throws Exception {
        log.error("-ErrorHandleController.handleAllException() invoked");

        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            log.debug("-Rethrow the EX");
            throw ex;
        }
        log.error("Error caught by handleAllException!", ex);

        ModelAndView model = new ModelAndView("common/invalid");
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("title", "Error Happened");
        model.getModel().put("message", "Error Happened");
        model.getModel().put("date", new Date());
        model.getModel().put("url", req.getRequestURL());
        model.addObject("errMsg", "this is Exception.class");

        ex.printStackTrace();
        return model;

    }


    /**
     * Handle 404 exception model and view.
     *
     * @param request  the request
     * @param response the response
     * @param ex       the ex
     * @return the model and view
     */
//    @ExceptionHandler({NoHandlerFoundException.class,NoSuchRequestHandlingMethodException.class})
    public ModelAndView handle404Exception(HttpServletRequest request, HttpServletResponse response, Exception ex) {

        log.error("-ErrorHandleController.handle404Exception() invoked");
        ModelAndView model = new ModelAndView("common/404");
        model.getModel().put("status", response.getStatus());
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("title", "ERROR_404");
        model.getModel().put("message", "ERROR MSG");
        model.getModel().put("date", new Date());

        return model;
    }


    /**
     * 处理访问受限
     *
     * @param request  request
     * @param response response
     * @param ex       exception
     * @return model model and view
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws ServletException, IOException {

        //记录活动日志
        SecurityLogHelper.LogHIGHRIGHT(request, LogActionType.ADMIN,"","AccessDenied","");

        if (!request.authenticate(response)) {
            RequestCache requestCache = new HttpSessionRequestCache();
            requestCache.saveRequest(request, response);
            //尚未登录，转到登录页面
            return null;
        }
        ModelAndView model = new ModelAndView("error");

        // 401 Access Denied , 403 Forbidden
        model.getModel().put("status", 403);
        model.getModel().put("path", request.getRequestURL());
        model.getModel().put("path", request.getRequestURL());
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("message", "您访问了需要更高权限的内容，请检查您登陆的账号");
        model.getModel().put("timestamp", new Date());

        return model;
    }


    /**
     * Handle method not supported exception model and view.
     *
     * @param ex the ex
     * @return the model and view
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleMethodNotSupportedException(Exception ex) {
        log.error("handleMethodNotSupportedException() invoked");
//        ModelAndView model = new ModelAndView("common/temp");
        ModelAndView model = new ModelAndView("error");
        model.getModel().put("error", ex.getMessage());
        model.getModel().put("title", "MethodNotSupported");
        model.getModel().put("message", "MethodNotSupported");
        model.getModel().put("date", new Date());
        model.getModel().put("status", "405");

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
