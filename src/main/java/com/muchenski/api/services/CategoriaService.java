package com.muchenski.api.services;

import org.springframework.stereotype.Service;

import com.muchenski.api.domain.Categoria;
import com.muchenski.api.repositories.CategoriaRepository;

@Service
public class CategoriaService extends AbstractService<Categoria, Long, CategoriaRepository> {

	public CategoriaService(CategoriaRepository repository) {
		super(repository);
	}

}
