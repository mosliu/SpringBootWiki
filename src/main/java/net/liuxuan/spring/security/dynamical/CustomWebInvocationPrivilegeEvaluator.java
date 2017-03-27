package net.liuxuan.spring.security.dynamical;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * Copyright (c) 2010-2017.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.dynamical.CustomWebInvocationPrivilegeEvaluator
 * 功能: 用于支持<div sec:authorize-url='/other1.jsp' href="/other1.jsp">权限test1</div> 这样的权限配置检查项
 * 版本:	@version 1.0
 * 编制日期: 2017/3/15 15:34
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2017/3/15  |    Moses       |     Created
 */
@Component
public class CustomWebInvocationPrivilegeEvaluator implements
        WebInvocationPrivilegeEvaluator {

    // ~ Static fields/initializers
    // =====================================================================================

    protected static final Log logger = LogFactory
            .getLog(CustomWebInvocationPrivilegeEvaluator.class);

    // ~ Instance fields
    // ================================================================================================

    private final AbstractSecurityInterceptor securityInterceptor;

    // ~ Constructors
    // ===================================================================================================

    @Autowired
    public CustomWebInvocationPrivilegeEvaluator(
            AbstractSecurityInterceptor securityInterceptor) {
        Assert.notNull(securityInterceptor, "SecurityInterceptor cannot be null");
        Assert.isTrue(
                FilterInvocation.class.equals(securityInterceptor.getSecureObjectClass()),
                "AbstractSecurityInterceptor does not support FilterInvocations");
        Assert.notNull(securityInterceptor.getAccessDecisionManager(),
                "AbstractSecurityInterceptor must provide a non-null AccessDecisionManager");

        this.securityInterceptor = securityInterceptor;
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * Determines whether the user represented by the supplied <tt>Authentication</tt>
     * object is allowed to invoke the supplied URI.
     *
     * @param uri the URI excluding the context path (a default context path setting will
     *            be used)
     */
    public boolean isAllowed(String uri, Authentication authentication) {
        return isAllowed(null, uri, null, authentication);
    }

    /**
     * Determines whether the user represented by the supplied <tt>Authentication</tt>
     * object is allowed to invoke the supplied URI, with the given .
     * <p>
     * Note the default implementation of <tt>FilterInvocationSecurityMetadataSource</tt>
     * disregards the <code>contextPath</code> when evaluating which secure object
     * metadata applies to a given request URI, so generally the <code>contextPath</code>
     * is unimportant unless you are using a custom
     * <code>FilterInvocationSecurityMetadataSource</code>.
     *
     * @param uri            the URI excluding the context path
     * @param contextPath    the context path (may be null, in which case a default value
     *                       will be used).
     * @param method         the HTTP method (or null, for any method)
     * @param authentication the <tt>Authentication</tt> instance whose authorities should
     *                       be used in evaluation whether access should be granted.
     * @return true if access is allowed, false if denied
     */
    public boolean isAllowed(String contextPath, String uri, String method,
                             Authentication authentication) {
        Assert.notNull(uri, "uri parameter is required");

        FilterInvocation fi = new FilterInvocation(contextPath, uri, method);
        Collection<ConfigAttribute> attrs = securityInterceptor
                .obtainSecurityMetadataSource().getAttributes(fi);

        if (attrs == null) {
            if (securityInterceptor.isRejectPublicInvocations()) {
                return false;
            }

            return true;
        }

        if (authentication == null) {
            return false;
        }

        try {
            securityInterceptor.getAccessDecisionManager().decide(authentication, fi,
                    attrs);
        } catch (AccessDeniedException unauthorized) {
            if (logger.isDebugEnabled()) {
                logger.debug(fi.toString() + " is denied for " + authentication.toString());
            }
            if (logger.isTraceEnabled()) {
                logger.trace(unauthorized);
            }

            return false;
        }

        return true;
    }
}
