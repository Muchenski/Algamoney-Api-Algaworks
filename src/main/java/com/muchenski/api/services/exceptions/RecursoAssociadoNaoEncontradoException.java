package com.muchenski.api.services.exceptions;

public class RecursoAssociadoNaoEncontradoException extends ServicoException {

	private static final long serialVersionUID = 1L;

	public RecursoAssociadoNaoEncontradoException(String classePrincipal, String classeAssociada, Object id) {
		super(String.format(
				"O recurso '%s' está inválido! Não foi encontrado(a) o registro de '%s' associado ao %s. Id de %s = %s",
				classePrincipal, classeAssociada, classePrincipal, classeAssociada, id));
	}

}
