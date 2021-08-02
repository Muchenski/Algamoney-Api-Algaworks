package com.muchenski.api.services;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.muchenski.api.domain.Pessoa;
import com.muchenski.api.repositories.PessoaRepository;

@Service
public class PessoaService extends AbstractService<Pessoa, Long, PessoaRepository> {

	public PessoaService(PessoaRepository repository) {
		super(repository);
	}

	@Override
	public Pessoa cadastrar(Pessoa newDto) {
		newDto.getContatos().forEach(c -> c.setPessoa(newDto));
		return super.cadastrar(newDto);
	}

	@Override
	public Pessoa atualizarPorId(Long id, Pessoa updateDto) {
		Pessoa destino = obterPorId(id);

		destino.getContatos().clear();
		destino.getContatos().addAll(updateDto.getContatos());
		destino.getContatos().forEach(c -> c.setPessoa(destino));

		BeanUtils.copyProperties(updateDto, destino, "id", "contatos");

		return repository.save(destino);
	}

	public Pessoa alterarStatus(Long id) {
		Pessoa destino = obterPorId(id);
		destino.setStatus(!destino.getStatus());
		return repository.save(destino);
	}

	public Page<Pessoa> obterPorNome(String nomeFilter, Pageable pageable) {
		return repository.obterPorNome(nomeFilter, pageable);
	}
}
