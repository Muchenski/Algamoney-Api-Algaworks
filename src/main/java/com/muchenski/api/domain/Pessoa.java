package com.muchenski.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pessoa")
public class Pessoa extends BaseModel {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Length(min = 3, max = 50)
	private String nome;

	@CPF
	private String cpf;

	@JsonIgnore
	@OneToMany(mappedBy = "pessoa")
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	@Embedded
	private Endereco endereco;

	@NotNull
	public Boolean status;

	@JsonIgnoreProperties(value = "pessoa")
	@Valid
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Contato> contatos = new ArrayList<Contato>();

	public Pessoa() {
		super();
	}

	public Pessoa(Long id, String nome, String cpf, Boolean status) {
		super(id);
		this.nome = nome;
		this.cpf = cpf;
		this.status = status;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

}
