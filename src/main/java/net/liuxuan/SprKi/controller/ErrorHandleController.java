package net.liuxuan.SprKi.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
@Controller
public class ErrorHandleController implements ErrorController {
//public class ErrorHandleController  {

    private static final String ERROR_PATH = "/error";


    @RequestMapping(value = ERROR_PATH)
    public String handleError(Map<String, Object> model) {
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


    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView model = new ModelAndView("common/temp");
        model.getModel().put("message", "INVALID MSG");
        model.getModel().put("title", "INVALID");
        model.getModel().put("date", new Date());
        model.addObject("errMsg", "this is Exception.class");

        return model;

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
