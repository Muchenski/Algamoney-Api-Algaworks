package com.muchenski.api.resources.exceptions;

public class ViaCepApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ViaCepApiException(String mensagem) {
		super(mensagem);
	}

}
