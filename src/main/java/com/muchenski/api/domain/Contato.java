package com.muchenski.api.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

@Entity
@Table(name = "contato")
public class Contato extends BaseModel {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String nome;

	@NotNull
	@Email
	private String email;

	@NotEmpty
	private String telefone;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

	public Contato() {
		super();
	}

	public Contato(Long id, String nome, String email, String telefone, Pessoa pessoa) {
		super(id);
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.pessoa = pessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
