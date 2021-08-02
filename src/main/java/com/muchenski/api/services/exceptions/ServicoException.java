package com.muchenski.api.services.exceptions;

public class ServicoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServicoException(String mensagem) {
		super(mensagem);
	}

}
