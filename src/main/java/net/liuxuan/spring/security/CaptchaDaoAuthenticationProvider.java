package net.liuxuan.spring.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * Copyright (c) 2010-2016.  by Liuxuan   All rights reserved. <br/>
 * ***************************************************************************
 * 源文件名:  net.liuxuan.spring.security.CaptchaDaoAuthenticationProvider
 * 功能:
 * 版本:	@version 1.0
 * 编制日期: 2016/4/6 14:36
 * 修改历史: (主要历史变动原因及说明)
 * YYYY-MM-DD |    Author      |	 Change Description
 * 2016/4/6  |    Moses       |     Created
 */
public class CaptchaDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        Object obj = authentication.getDetails();
        if (!(obj instanceof CaptchaAuthenticationDetails)) {
            //HACK method,用于后台登录验证
            if (obj instanceof WebAuthenticationDetails) {
//                super.additionalAuthenticationChecks(userDetails, authentication);
                return;
            } else {
                throw new InsufficientAuthenticationException(
                        "Captcha details not found.");
            }
        } else {
            CaptchaAuthenticationDetails captchaDetails = (CaptchaAuthenticationDetails) obj;
            String captcha = captchaDetails.getCaptcha();
            if (captcha != null) {
                String expected = captcha;
                String actual = captchaDetails.getAnswer();
                if (!expected.equals(actual)) {
//                token=null;
                    authentication.eraseCredentials();
                    throw new BadCaptchaException("Captcha does not match.");
                }
            }
//            super.additionalAuthenticationChecks(userDetails, authentication);
        }

        //no longer use super.additionalAuthenticationChecks(userDetails, token);

        Object salt = null;

        if (super.getSaltSource() != null) {
            salt = super.getSaltSource().getSalt(userDetails);
        }
        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!super.getPasswordEncoder().isPasswordValid(userDetails.getPassword(),
                presentedPassword, salt)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }


    }


}
