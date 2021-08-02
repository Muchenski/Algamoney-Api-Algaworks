package com.muchenski.api.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.muchenski.api.domain.enums.TipoLancamento;

@Entity
@Table(name = "lancamento")
public class Lancamento extends BaseModel {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Length(min = 5, max = 100)
	private String descricao;

	@NotNull
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;

	@NotNull
	@PositiveOrZero
	private Double valor;

	private String observacao;

	@NotNull
	private Integer tipoLancamento;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@JsonIgnoreProperties("contatos")
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

	public Lancamento() {
		super();
	}

	public Lancamento(Long id, String descricao, LocalDate dataVencimento, LocalDate dataPagamento, Double valor,
			String observacao, TipoLancamento tipoLancamento, Categoria categoria, Pessoa pessoa) {
		super(id);
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.observacao = observacao;
		this.tipoLancamento = tipoLancamento.getCodigo();
		this.categoria = categoria;
		this.pessoa = pessoa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoLancamento getTipoLancamento() {
		return TipoLancamento.obterPorId(tipoLancamento);
	}

	public void setTipoLancamento(TipoLancamento tipoLancamento) {
		this.tipoLancamento = tipoLancamento.getCodigo();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@JsonIgnore
	public Boolean isReceita() {
		return tipoLancamento == TipoLancamento.RECEITA.getCodigo();
	}
}
