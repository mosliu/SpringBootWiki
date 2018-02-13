package net.liuxuan.spring.security;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

public class CaptchaAuthenticationDetailsSource implements
		AuthenticationDetailsSource<HttpServletRequest, CaptchaAuthenticationDetails> {

	@Override
	public CaptchaAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new CaptchaAuthenticationDetails(context);
	}
}
