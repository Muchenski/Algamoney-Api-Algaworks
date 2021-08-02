package com.muchenski.api.repositories.queries;

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

import com.muchenski.api.domain.Pessoa;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Pessoa> obterPorNome(String nomeFilter, Pageable pageable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);

		Predicate[] predicates = criarRestricoes(nomeFilter, builder, root);

		criteria.where(predicates);

		TypedQuery<Pessoa> query = entityManager.createQuery(criteria);

		adicionarRestricoesDePaginacao(query, pageable);

		Long totalDeRegistrosEncontrados = obterTotalDeRegistrosEncontrados(nomeFilter);

		return new PageImpl<Pessoa>(query.getResultList(), pageable, totalDeRegistrosEncontrados);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int numeroDeRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * numeroDeRegistrosPorPagina;
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(numeroDeRegistrosPorPagina);
	}

	private Long obterTotalDeRegistrosEncontrados(String nomeFilter) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		Predicate[] predicates = criarRestricoes(nomeFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));
		return entityManager.createQuery(criteria).getSingleResult();
	}

	private Predicate[] criarRestricoes(String nomeFilter, CriteriaBuilder builder, Root<Pessoa> root) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(nomeFilter)) {
			nomeFilter = nomeFilter.strip().toLowerCase();
			predicates.add(builder.like(builder.lower(root.get("nome")), "%" + nomeFilter + "%"));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
