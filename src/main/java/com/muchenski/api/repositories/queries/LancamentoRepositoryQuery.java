package com.muchenski.api.repositories.queries;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.muchenski.api.domain.Lancamento;
import com.muchenski.api.dtos.LancamentoDTO;
import com.muchenski.api.dtos.LancamentoEstatisticaCategoriaDTO;
import com.muchenski.api.repositories.filters.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public abstract Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
	
	public abstract Page<LancamentoDTO> filtrarDTO(LancamentoFilter filter, Pageable pageable);

	public abstract List<LancamentoEstatisticaCategoriaDTO> filtrarPorCategoria(LocalDate dataReferencia);
}