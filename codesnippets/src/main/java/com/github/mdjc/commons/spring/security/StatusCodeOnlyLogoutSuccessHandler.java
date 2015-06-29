package com.github.mdjc.commons.spring.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class StatusCodeOnlyLogoutSuccessHandler implements LogoutSuccessHandler {
	private final int statusCode;

	public StatusCodeOnlyLogoutSuccessHandler(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		response.setStatus(statusCode);
		response.flushBuffer();
	}
}