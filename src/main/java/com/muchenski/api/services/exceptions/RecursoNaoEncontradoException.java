package com.muchenski.api.services.exceptions;

public class RecursoNaoEncontradoException extends ServicoException {

	private static final long serialVersionUID = 1L;

	public RecursoNaoEncontradoException(String classe, Object id) {
		super(String.format("O recurso '%s' não foi encontrado(a)! Identificador = %s", classe, id));
	}
}
