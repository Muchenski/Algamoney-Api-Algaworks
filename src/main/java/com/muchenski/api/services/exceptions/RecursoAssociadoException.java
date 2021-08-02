package com.muchenski.api.services.exceptions;

public class RecursoAssociadoException extends ServicoException {

	private static final long serialVersionUID = 1L;

	public RecursoAssociadoException(String classe, Object id) {
		super(String.format("O recurso '%s' está associado(a) com outro(s) registro(s)! Identificador = %s", classe,
				id));
	}
}
