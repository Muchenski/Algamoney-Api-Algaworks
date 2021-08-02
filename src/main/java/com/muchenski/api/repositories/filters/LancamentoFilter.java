package com.muchenski.api.repositories.filters;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {

	private String descricao;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate minDataVencimento;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate maxDataVencimento;

	public LancamentoFilter() {
	}

	public LancamentoFilter(String descricao, LocalDate minDataVencimento, LocalDate maxDataVencimento) {
		this.descricao = descricao;
		this.minDataVencimento = minDataVencimento;
		this.maxDataVencimento = maxDataVencimento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getMinDataVencimento() {
		return minDataVencimento;
	}

	public void setMinDataVencimento(LocalDate minDataVencimento) {
		this.minDataVencimento = minDataVencimento;
	}

	public LocalDate getMaxDataVencimento() {
		return maxDataVencimento;
	}

	public void setMaxDataVencimento(LocalDate maxDataVencimento) {
		this.maxDataVencimento = maxDataVencimento;
	}

}
