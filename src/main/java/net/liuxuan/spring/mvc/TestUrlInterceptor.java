/*
 * Copyright (c) 2010-2016.  by Moses   All rights reserved.
 *
 */

package net.liuxuan.spring.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Moses on 2016/2/5.
 */

public class TestUrlInterceptor implements HandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(TestUrlInterceptor.class);

    public TestUrlInterceptor() {
        log.debug("--------------- TestUrlInterceptor initialize -------------");

//        System.out.println("--------------- TestUrlInterceptor initialize -------------");
    }

    /**
     * Intercept the execution of a handler. Called after HandlerMapping determined
     * an appropriate handler object, but before HandlerAdapter invokes the handler.
     *
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can decide to abort the execution chain,
     * typically sending a HTTP error or writing a custom response.
     *
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link org.springframework.web.servlet.AsyncHandlerInterceptor}.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} if the execution chain should proceed with the
     * next interceptor or the handler itself. Else, DispatcherServlet assumes
     * that this interceptor has already dealt with the response itself.
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


//        if(request.getRequestURI().equals("/error")){
//            System.out.println("------------------error path");
//            //request.getRequestDispatcher("/invalidPage");
//            response.sendRedirect("/invalidPage");
//        }
//        HandlerMethod  hm = (HandlerMethod) handler;

        String query = request.getQueryString();
        query = (query == null) ? "" : query;
//        String url = request.getMethod() + "  " + request.getRequestURL().toString() + "?" + query;

        log.debug("--PreCheck,url:{} {}? " , request.getMethod(),request.getRequestURL().toString(),query);

//        System.out.println("--UrlInterceptor Pre--"+url);

        return true;
    }

    /**
     * Intercept the execution of a handler. Called after HandlerAdapter actually
     * invoked the handler, but before the DispatcherServlet renders the view.
     * Can expose additional model objects to the view via the given ModelAndView.
     *
     * <p>DispatcherServlet processes a handler in an execution chain, consisting
     * of any number of interceptors, with the handler itself at the end.
     * With this method, each interceptor can post-process an execution,
     * getting applied in inverse order of the execution chain.
     *
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link org.springframework.web.servlet.AsyncHandlerInterceptor}.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler handler (or {@link HandlerMethod}) that started async
     * execution, for type and/or instance examination
     * @param modelAndView the {@code ModelAndView} that the handler returned
     * (can also be {@code null})
     * @throws Exception in case of errors
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {


        if (modelAndView == null) {
            //可能是Restful Services

//            modelAndView = new ModelAndView("error");
//            throw new Exception("not find this path!!!!!");
        }

        String viewname =modelAndView==null?"NULL": modelAndView.getViewName();
        String user = request.getRemoteUser();
        user = user ==null?"NULL":user;
        log.debug("--Handler:{}" ,handler.toString());
        log.debug("--Viewname: {} & User: {}--" , viewname,user);
//        System.out.println("-------------- TestUrlInterceptor post url -----------------" + modelAndView.getViewName());
    }

    /**
     * Callback after completion of request processing, that is, after rendering
     * the view. Will be called on any outcome of handler execution, thus allows
     * for proper resource cleanup.
     *
     * <p>Note: Will only be called if this interceptor's {@code preHandle}
     * method has successfully completed and returned {@code true}!
     *
     * <p>As with the {@code postHandle} method, the method will be invoked on each
     * interceptor in the chain in reverse order, so the first interceptor will be
     * the last to be invoked.
     *
     * <p><strong>Note:</strong> special considerations apply for asynchronous
     * request processing. For more details see
     * {@link org.springframework.web.servlet.AsyncHandlerInterceptor}.
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler handler (or {@link HandlerMethod}) that started async
     * execution, for type and/or instance examination
     * @param ex exception thrown on handler execution, if any
     * @throws Exception in case of errors
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        if (ex != null) {
//            ex.printStackTrace();
            log.debug("--ErrorFound:{} Happened@ {}" ,ex.getMessage(),handler.toString());
//            System.out.println("--ErrorFount" + ex.getMessage());
        }
    }
}
