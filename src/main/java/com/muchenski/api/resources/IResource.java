package com.muchenski.api.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.muchenski.api.domain.IModel;

public interface IResource<T extends IModel<ID>, ID> {

	public abstract ResponseEntity<?> cadastrar(T newDto, HttpServletResponse response);

	public abstract ResponseEntity<?> obterPorId(ID id);

	public abstract ResponseEntity<?> obterTodos();

	public abstract ResponseEntity<?> atualizarPorId(ID id, T updateDto);

	public abstract ResponseEntity<?> removerPorId(ID id);
}
