package com.muchenski.api.dtos;

import java.io.Serializable;

import com.muchenski.api.domain.Categoria;

public class LancamentoEstatisticaCategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Categoria categoria;

	private Double total;

	public LancamentoEstatisticaCategoriaDTO() {

	}

	public LancamentoEstatisticaCategoriaDTO(Categoria categoria, Double total) {
		this.categoria = categoria;
		this.total = total;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
