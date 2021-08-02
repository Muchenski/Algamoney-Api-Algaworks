package com.muchenski.api.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "permissao")
public class Permissao extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String descricao;

	@JsonIgnore
	@ManyToMany(mappedBy = "permissoes")
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public Permissao() {
		super();
	}

	public Permissao(Long id, String descricao) {
		super(id);
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
