package com.muchenski.api.resources;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muchenski.api.config.property.ApiProperty;

@RestController
@RequestMapping(value = "/token")
public class TokenResource {

	@Autowired
	private ApiProperty apiProperty;

	// Removendo o refreshToken do cookie.
	@DeleteMapping(value = "/revoke")
	public ResponseEntity<?> revoke(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);

		cookie.setSecure(apiProperty.getSecurity().isEnableHttps());

		cookie.setPath(httpServletRequest.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		httpServletResponse.addCookie(cookie);
		return ResponseEntity.noContent().build();
	}
}
