package com.github.prgrms.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	static String _403 = "{\"success\":false,\"response\":null,\"error\":{\"message\":\"Forbidden\",\"status\":403}}";

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setHeader("content-type", "application/json");
		response.getWriter().write(_403);
		response.getWriter().flush();
		response.getWriter().close();
	}

}
