package net.liuxuan.spring.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;

public class CaptchaAuthenticationDetailsSource implements
		AuthenticationDetailsSource<HttpServletRequest, CaptchaAuthenticationDetails> {

	@Override
	public CaptchaAuthenticationDetails buildDetails(HttpServletRequest context) {
		return new CaptchaAuthenticationDetails(context);
	}
}
