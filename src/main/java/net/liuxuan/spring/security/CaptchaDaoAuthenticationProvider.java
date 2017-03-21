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
                                                  UsernamePasswordAuthenticationToken token)
            throws AuthenticationException {
        Object obj = token.getDetails();
        if (!(obj instanceof CaptchaAuthenticationDetails)) {
            //HACK method,用于后台登录验证
            if(obj instanceof WebAuthenticationDetails){
                super.additionalAuthenticationChecks(userDetails, token);
                return;
            }

            throw new InsufficientAuthenticationException(
                    "Captcha details not found.");
        }

        CaptchaAuthenticationDetails captchaDetails = (CaptchaAuthenticationDetails) obj;
        String captcha = captchaDetails.getCaptcha();
        if (captcha != null) {
            String expected = captcha;
            String actual = captchaDetails.getAnswer();
            if (!expected.equals(actual)) {
//                token=null;
                token.eraseCredentials();
                throw new BadCaptchaException("Captcha does not match.");
            }
        }
        super.additionalAuthenticationChecks(userDetails, token);
    }
}
