package com.muchenski.api.token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.muchenski.api.config.property.ApiProperty;

// Esta classe irá interceptar as respostas que contém o tipo OAuth2AccesToken em seu corpo.

// ResponseBodyAdvice<Tipo-do-corpo-da-resposta-que-deseja-interceptar>

// Esta classe irá interceptar a geração do token, irá colocar o refresh token em um cookie, e depois
// no corpo da resposta.
@ControllerAdvice
public final class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private ApiProperty apiProperty;
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

		// Obtendo o e verificando se método chamado, que contém retorno
		// ResponseEntity<OAuth2AccessToken> tem o nome de "postAccesToken".
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	// Só é executado quando o método "supports" retornar "true".
	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {

		HttpServletRequest httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse httpServletResponse = ((ServletServerHttpResponse) response).getServletResponse();

		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		String refreshToken = body.getRefreshToken().toString();

		adicionarRefreshTokenNoCookie(refreshToken, httpServletRequest, httpServletResponse);
		removerRefreshTokenDoBody(token);

		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseCookie cookieRefreshToken = ResponseCookie.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(apiProperty.getSecurity().isEnableHttps())
				.path(request.getContextPath() + "/oauth/token")
				.maxAge(24 * 3600 * 30)
				.build();
		response.setHeader(HttpHeaders.SET_COOKIE, cookieRefreshToken.toString());
	}

}