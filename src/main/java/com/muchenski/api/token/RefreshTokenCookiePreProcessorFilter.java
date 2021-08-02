package com.muchenski.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// Após o cliente estar logado e tiver o refresh token no cookie, quando ele fizer uma requisição
// com o "grant_type" "refresh_token", para /oauth/token, automaticamente o refresh token do cookie será inserido
// como parâmetro da requisição, solicitando um novo access token e mantendo o usuário logado.
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		if (httpServletRequest.getRequestURI().equals("/oauth/token")
				&& httpServletRequest.getParameter("grant_type") != null
				&& httpServletRequest.getParameter("grant_type").equals("refresh_token")
				&& httpServletRequest.getCookies() != null) {
			
			for(Cookie cookie : httpServletRequest.getCookies()) {
				if(cookie.getName().equals("refreshToken")) {
					String refreshToken = cookie.getValue();
					httpServletRequest = new MyServletRequestWrapper(httpServletRequest, refreshToken);
				}
			}
		}
		
		chain.doFilter(httpServletRequest, response);
	}

	// Para podermos manipular a requisição.
	private class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;
		
		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		// Manipulando os parâmetros da requisição.
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> parameterMap = new ParameterMap<String, String[]>();
			parameterMap.putAll(getRequest().getParameterMap());
			parameterMap.put("refresh_token", new String[] {refreshToken});
			parameterMap.setLocked(true);
			return parameterMap;
		}
	}
}
