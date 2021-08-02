package com.muchenski.api.resources.exceptions.enums;

public enum TipoErro {
	REQUISICAO_COM_ERROS(0, "Corpo da requisição inválido"), RECURSO_NAO_ENCONTRADO(1, "Recurso não encontrado"),
	RECURSO_ASSOCIADO(2, "Recurso associado a outros registros"),
	WEB_SERVICE_EXTERNO(3, "Ocorreu um erro relacionado a um serviço externo"), PESSOA_INATIVA(4, "Pessoa inativa"),
	RECURSO_ASSOCIADO_NAO_ENCONTRADO(5, "Um ou mais recurso(s) associado(s) ao registro não foram encontrado(s)"),
	PARAMETRO_DE_REQUISICAO_INVALIDO(6, "Um ou mais parâmetro(s) da requisição não cumprem com os critérios do sistema");

	private Integer codigo;
	private String descricao;

	private TipoErro(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static TipoErro obterPorId(Integer id) {
		if (id == null) {
			return null;
		}

		for (TipoErro tipoErro : TipoErro.values()) {
			if (tipoErro.getCodigo().equals(id)) {
				return tipoErro;
			}
		}

		throw new IllegalArgumentException(
				String.format("%s não foi encontrado(a)! Identificador = %s", TipoErro.class.getSimpleName(), id));
	}
}
