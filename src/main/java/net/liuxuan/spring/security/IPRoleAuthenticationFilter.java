package net.liuxuan.spring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved.
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.IPRoleAuthenticationFilter
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/2/26 9:14
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/2/26  |    Moses       |     Created
 */

//@ConfigurationProperties(value="ip.properties",prefix = "ip")
//@Component
public class IPRoleAuthenticationFilter extends OncePerRequestFilter {
    @Value("${ip.target}")
    private String targetRole;
    @Value("${ip.allowedIPAddresses}")
    private String allowedIPAddresses;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {


        boolean shouldCheck = true;
        // if we should check IP, then check
//        System.out.println(targetRole==null);
//        System.out.println(allowedIPAddresses==null);
//        System.out.println(req.getRemoteAddr());
        if(shouldCheck && allowedIPAddresses.contains(req.getRemoteAddr())){
            throw new AccessDeniedException("Access has been denied for your IP address: " + req.getRemoteAddr());
        }
//        if (shouldCheck && allowedIPAddresses.size() > 0) {
//            boolean shouldAllow = false;
//            for (String ipAddress : allowedIPAddresses) {
//                if (req.getRemoteAddr().equals(ipAddress)) {
//                    shouldAllow = true;
//                    break;
//                }
//            }
//
//            if (!shouldAllow) {
//                // fail the request
//                throw new AccessDeniedException("Access has been denied for your IP address: " + req.getRemoteAddr());
//            }
//        }


        // before we allow the request to proceed, we'll first get the user's role
        // and see if it's an administrator
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && targetRole != null) {

            // look if the user is the target role
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals(targetRole)) {
                    shouldCheck = true;
                    break;
                }
            }

        } else {

            logger.warn("-The IPRoleAuthenticationFilter should be placed after the user has been authenticated in the filter chain.   "+req.getRequestURL());
        }
        chain.doFilter(req, res);
    }
// accessors (getters and setters) omitted
}
