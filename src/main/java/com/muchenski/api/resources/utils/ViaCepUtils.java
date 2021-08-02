package com.muchenski.api.resources.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.muchenski.api.domain.Endereco;
import com.muchenski.api.domain.Pessoa;
import com.muchenski.api.resources.exceptions.ViaCepApiException;

public class ViaCepUtils {

	public static String baseUrl = "https://viacep.com.br/ws/";

	public static <T extends Pessoa> void setarEnderecoViaCEP(T dto) {
		RestTemplate restTemplate = new RestTemplate();

		Endereco endereco = dto.getEndereco();
		if (endereco == null) {
			return;
		}

		String cep = endereco.getCep();
		if (!StringUtils.hasText(cep)) {
			return;
		}
		
		String complemento = endereco.getComplemento();
		String numero = endereco.getNumero();
		cep = cep.replaceAll("[/\\D/g]", "");
		
		try {
			endereco = restTemplate.getForEntity(baseUrl + cep + "/json/", Endereco.class).getBody();
			endereco.setComplemento(complemento);
			endereco.setNumero(numero);
			dto.setEndereco(endereco);
		} catch (RestClientException e) {
			throw new ViaCepApiException(e.getMostSpecificCause().getMessage());
		}
	}
}
