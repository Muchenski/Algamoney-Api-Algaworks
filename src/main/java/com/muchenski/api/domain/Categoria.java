package com.muchenski.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoria")
public class Categoria extends BaseModel {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Length(min = 3, max = 30)
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "categoria")
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	public Categoria() {
		super();
	}

	public Categoria(Long id, String nome) {
		super(id);
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}
