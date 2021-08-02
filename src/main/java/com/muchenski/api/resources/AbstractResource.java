package com.muchenski.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.muchenski.api.domain.IModel;
import com.muchenski.api.events.RecursoCriadoEvent;
import com.muchenski.api.services.IService;

@Component
public abstract class AbstractResource<T extends IModel<ID>, ID, S extends IService<T, ID>>
		implements IResource<T, ID> {

	protected S service;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	public AbstractResource(S service) {
		this.service = service;
	}

	@PostMapping
	@Override
	public ResponseEntity<?> cadastrar(@Valid @RequestBody T newDto, HttpServletResponse response) {
		T getDto = service.cadastrar(newDto);
		eventPublisher.publishEvent(new RecursoCriadoEvent<IModel<ID>, ID>(getDto, response, getDto.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(getDto);
	}

	@GetMapping(value = "/{id}")
	@Override
	public ResponseEntity<?> obterPorId(@PathVariable ID id) {
		return ResponseEntity.ok(service.obterPorId(id));
	}

	@GetMapping
	@Override
	public ResponseEntity<?> obterTodos() {
		List<T> resultados = service.obterTodos();
		return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
	}

	@PutMapping(path = "/{id}")
	@Override
	public ResponseEntity<?> atualizarPorId(@PathVariable ID id, @Valid @RequestBody T updateDto) {
		return ResponseEntity.ok(service.atualizarPorId(id, updateDto));
	}

	@DeleteMapping(path = "/{id}")
	@Override
	public ResponseEntity<?> removerPorId(@PathVariable ID id) {
		service.removerPorId(id);
		return ResponseEntity.noContent().build();
	}

}
