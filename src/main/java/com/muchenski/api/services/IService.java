package com.muchenski.api.services;

import java.util.List;

import com.muchenski.api.domain.IModel;

public interface IService<T extends IModel<ID>, ID> {

	public abstract T cadastrar(T newDto);

	public abstract T obterPorId(ID id);

	public abstract List<T> obterTodos();

	public abstract T atualizarPorId(ID id, T updateDto);

	public abstract void removerPorId(ID id);

	public abstract List<T> salvarTodos(List<T> newDtos);
}
