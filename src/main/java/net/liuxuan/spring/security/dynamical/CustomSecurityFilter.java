package net.liuxuan.spring.security.dynamical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.dynamacal.CustomSecurityFilter
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2017/3/15 9:01
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/3/15  |    Moses       |     Created
 */
@Component("customSecurityFilter")
public class CustomSecurityFilter extends FilterSecurityInterceptor {



    @Autowired
    public void setMyAccessDecisionManager(CustomAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

// ~ Static fields/initializers
    // =====================================================================================

    private static final String FILTER_APPLIED = "__spring_security_myFilterSecurityInterceptor_filterApplied";


    @Autowired
    public CustomSecurityFilter(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        super.setSecurityMetadataSource(securityMetadataSource);
    }


    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a
     * client request for a resource at the end of the chain. The FilterChain
     * passed in to this method allows the Filter to pass on the request and
     * response to the next entity in the chain.
     * <p>
     * A typical implementation of this method would follow the following
     * pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using
     * the FilterChain object (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the
     * next entity in the filter chain to block the request processing<br>
     * 5. Directly set headers on the response after invocation of the next
     * entity in the filter chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      if an I/O error occurs during this filter's
     *                          processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }


    /**
     * Indicates the type of secure objects the subclass will be presenting to the
     * abstract parent for processing. This is used to ensure collaborators wired to the
     * {@code AbstractSecurityInterceptor} all support the indicated secure object class.
     *
     * @return the type of secure object the subclass provides services for
     */
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }



    public void invoke(FilterInvocation fi) throws IOException, ServletException {

        if ((fi.getRequest() != null)
                && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
                && this.isObserveOncePerRequest()) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        }
        else {
            // first time this request being called, so perform security checking
            if (fi.getRequest() != null) {
                fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
            }

            InterceptorStatusToken token = super.beforeInvocation(fi);

            try {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            }
            finally {
                super.finallyInvocation(token);
            }

            super.afterInvocation(token, null);
        }

    }

    //****************************** getter and setter ******************************//


//    @Qualifier("securityMetadataSource")
//    @Autowired
//    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
//        super.setSecurityMetadataSource(securityMetadataSource);
//    }
}
