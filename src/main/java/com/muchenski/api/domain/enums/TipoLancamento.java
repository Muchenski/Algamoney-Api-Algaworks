package com.muchenski.api.domain.enums;

public enum TipoLancamento {
	RECEITA(0, "Receita"), DESPESA(1, "Despesas");

	private Integer codigo;
	private String descricao;

	private TipoLancamento(Integer codigo, String descricao) {
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

	public static TipoLancamento obterPorId(Integer id) {
		if (id == null) {
			return null;
		}

		for (TipoLancamento tipoLancamento : TipoLancamento.values()) {
			if (tipoLancamento.getCodigo().equals(id)) {
				return tipoLancamento;
			}
		}

		throw new IllegalArgumentException(
				String.format("%s não foi encontrado(a)! Identificador = %s", TipoLancamento.class.getSimpleName(), id));
	}
}
