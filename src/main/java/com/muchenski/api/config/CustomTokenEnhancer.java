package com.muchenski.api.config;

import java.util.HashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.muchenski.api.security.UserSS;

@Profile("oauth-security")
// Classe para atribuir mais informações ao nosso token.
public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		HashMap<String, Object> additionalInfo = new HashMap<String, Object>();
		additionalInfo.put("nome", ((UserSS) authentication.getPrincipal()).getNome());

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

		return accessToken;
	}

}
