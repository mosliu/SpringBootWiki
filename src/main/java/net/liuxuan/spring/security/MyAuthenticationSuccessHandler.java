package net.liuxuan.spring.security;

import net.liuxuan.supportsystem.entity.security.LogActionType;
import net.liuxuan.spring.Helper.SecurityLogHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.MyAuthenticationSuccessHandler
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/19 10:25
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM月DD |    Author      |	 Change Description
 * 2016/2/19 |    Moses       |     Created
 */
@Component
//public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private static Logger log = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    private RequestCache requestCache = new HttpSessionRequestCache();

    public MyAuthenticationSuccessHandler() {
    }

    /**
     * Constructor which sets the <tt>defaultTargetUrl</tt> property of the base class.
     *
     * @param defaultTargetUrl the URL to which the user should be redirected on
     *                         successful authentication.
     */
    public MyAuthenticationSuccessHandler(String defaultTargetUrl) {
        super(defaultTargetUrl);
    }

    /**
     * Calls the parent class {@code handle()} method to forward or redirect to the target
     * URL, and then calls {@code clearAuthenticationAttributes()} to remove any leftover
     * session data.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication auth)
            throws IOException, ServletException {

//        log.debug("===onAuthenticationSuccess()");

        //最后应为URL，当做用户名使用。
        SecurityLogHelper.LogLogStatus(request, LogActionType.LOGIN,request.getParameterMap(),"成功登陆","");

        SavedRequest savedRequest = requestCache.getRequest(request, response);
//        log.debug("======savedRequest is: {}",savedRequest);
        String targetUrl = getDefaultTargetUrl();
        if (savedRequest == null) {
//            log.debug("===savedRequest is null");
        } else {
            String targetUrlParam = getTargetUrlParameter();
//            log.debug("===TargetUrlParam is {}", targetUrlParam);
            if (isAlwaysUseDefaultTargetUrl() ||
                    (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
                requestCache.removeRequest(request, response);
            }else{
                targetUrl =savedRequest.getRedirectUrl();
            }


        }
        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//
//
//
//        String f = request.getParameter("f");
//        if (StringUtils.isNotEmpty(f)) {
//            if (f.equals("android")) {
//                response.setCharacterEncoding("UTF-8");
//                response.getWriter().write("登录成功" + auth.getName());
//            }
//
//        } else {
//            String ru = (String) request.getSession().getAttribute("ru");
//            request.getSession().removeAttribute("ru");
//            if (StringUtils.isNotEmpty(ru)) {
//                log.debug("--URL return Back to: {}",ru);
//                response.sendRedirect(ru);
//
//                //request.getRequestDispatcher(ru).forward(request, response);
//            } else {
//
//                request.getRequestDispatcher("/").forward(request, response);
//            }
//
//        }
//
//        handle(request, response, auth);
//        clearAuthenticationAttributes(request);

    }


    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }




}
