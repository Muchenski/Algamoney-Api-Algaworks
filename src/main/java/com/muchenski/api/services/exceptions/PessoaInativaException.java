package com.muchenski.api.services.exceptions;

public class PessoaInativaException extends ServicoException {

	private static final long serialVersionUID = 1L;

	public PessoaInativaException(String nome, Object id) {
		super(String.format("A pessoa '%s' está inativa, e por este motivo não pode realizar a ação! Id de %s = %s",
				nome, nome, id));
	}

}
