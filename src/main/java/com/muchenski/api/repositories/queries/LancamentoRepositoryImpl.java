package com.muchenski.api.repositories.queries;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.muchenski.api.domain.Lancamento;
import com.muchenski.api.dtos.LancamentoDTO;
import com.muchenski.api.dtos.LancamentoEstatisticaCategoriaDTO;
import com.muchenski.api.repositories.filters.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		Predicate[] predicates = criarRestricoes(filter, builder, root);

		criteria.where(predicates);

		TypedQuery<Lancamento> query = entityManager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		Long totalDeRegistrosEncontrados = obterTotalDeRegistrosEncontrados(filter);

		return new PageImpl<Lancamento>(query.getResultList(), pageable, totalDeRegistrosEncontrados);
	}

	@Override
	public Page<LancamentoDTO> filtrarDTO(LancamentoFilter filter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LancamentoDTO> criteria = builder.createQuery(LancamentoDTO.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(
				builder.construct(
						LancamentoDTO.class, 
						root.get("id"), 
						root.get("descricao"),
						root.get("dataVencimento"), 
						root.get("dataPagamento"), 
						root.get("valor"), 
						root.get("tipoLancamento"),
						root.get("categoria").get("nome"), 
						root.get("pessoa").get("nome")
				)
		);

		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);

		TypedQuery<LancamentoDTO> query = entityManager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		Long totalDeRegistrosEncontrados = obterTotalDeRegistrosEncontrados(filter);

		return new PageImpl<LancamentoDTO>(query.getResultList(), pageable, totalDeRegistrosEncontrados);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int numeroDeRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * numeroDeRegistrosPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(numeroDeRegistrosPorPagina);
	}

	private Long obterTotalDeRegistrosEncontrados(LancamentoFilter filter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		Predicate[] predicates = criarRestricoes(filter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}

	private Predicate[] criarRestricoes(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		String descricaoFilter = filter.getDescricao();
		if (StringUtils.hasText(descricaoFilter)) {
			descricaoFilter = descricaoFilter.strip().toLowerCase();
			predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + descricaoFilter + "%"));
		}

		LocalDate minDataVencimentoFilter = filter.getMinDataVencimento();
		if (minDataVencimentoFilter != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataVencimento"), minDataVencimentoFilter));
		}

		LocalDate maxDataVencimentoFilter = filter.getMaxDataVencimento();
		if (maxDataVencimentoFilter != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataVencimento"), maxDataVencimentoFilter));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	@Override
	public List<LancamentoEstatisticaCategoriaDTO> filtrarPorCategoria(LocalDate dataReferencia) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaCategoriaDTO> criteria = builder.createQuery(LancamentoEstatisticaCategoriaDTO.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(
				builder.construct(
						LancamentoEstatisticaCategoriaDTO.class, 
						root.get("categoria"),
						builder.sum(root.get("valor"))
				)
		);
		
		if(dataReferencia != null) {
			LocalDate primeiroDia = dataReferencia.withDayOfMonth(1);
			LocalDate ultimoDia = dataReferencia.withDayOfMonth(dataReferencia.lengthOfMonth());
			
			criteria.where(
					builder.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDia),
					builder.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDia)
			);
		}
		
		criteria.groupBy(root.get("categoria"));

		TypedQuery<LancamentoEstatisticaCategoriaDTO> query = entityManager.createQuery(criteria);
		
		return query.getResultList();
	}

}
