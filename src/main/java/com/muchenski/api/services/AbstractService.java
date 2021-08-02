package com.muchenski.api.services;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.muchenski.api.domain.IModel;
import com.muchenski.api.services.exceptions.RecursoAssociadoException;
import com.muchenski.api.services.exceptions.RecursoNaoEncontradoException;

@Component
public abstract class AbstractService<T extends IModel<ID>, ID, R extends CrudRepository<T, ID>>
		implements IService<T, ID> {

	protected Class<T> classeModel;

	protected R repository;

	@SuppressWarnings("unchecked")
	public AbstractService(R repository) {
		this.repository = repository;
		classeModel = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T cadastrar(T newDto) {
		newDto.setId(null);
		return repository.save(newDto);
	}

	@Override
	public T obterPorId(ID id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecursoNaoEncontradoException(classeModel.getSimpleName(), id));
	}

	@Override
	public List<T> obterTodos() {
		return (List<T>) repository.findAll();
	}

	@Override
	public T atualizarPorId(ID id, T updateDto) {
		T destino = obterPorId(id);
		BeanUtils.copyProperties(updateDto, destino, "id");
		return repository.save(destino);
	}

	@Override
	public void removerPorId(ID id) {
		obterPorId(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new RecursoAssociadoException(classeModel.getSimpleName(), id);
		}
	}

	@Override
	public List<T> salvarTodos(List<T> newDtos) {
		return (List<T>) repository.saveAll(newDtos);
	}

}
