package com.muchenski.api.repositories.queries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muchenski.api.domain.Pessoa;

public interface PessoaRepositoryQuery {

	public abstract Page<Pessoa> obterPorNome(String nomeFilter, Pageable pageable);

}
