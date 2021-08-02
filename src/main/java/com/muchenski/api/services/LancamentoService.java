package com.muchenski.api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.muchenski.api.domain.Categoria;
import com.muchenski.api.domain.Lancamento;
import com.muchenski.api.domain.Pessoa;
import com.muchenski.api.domain.Usuario;
import com.muchenski.api.dtos.LancamentoDTO;
import com.muchenski.api.dtos.LancamentoEstatisticaCategoriaDTO;
import com.muchenski.api.mail.Mailer;
import com.muchenski.api.repositories.LancamentoRepository;
import com.muchenski.api.repositories.UsuarioRepository;
import com.muchenski.api.repositories.filters.LancamentoFilter;
import com.muchenski.api.services.exceptions.PessoaInativaException;
import com.muchenski.api.services.exceptions.RecursoAssociadoNaoEncontradoException;
import com.muchenski.api.services.exceptions.RecursoNaoEncontradoException;

@Service
public class LancamentoService extends AbstractService<Lancamento, Long, LancamentoRepository> {

	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private Mailer mailer;

	public LancamentoService(LancamentoRepository repository) {
		super(repository);
	}

	@Override
	public Lancamento cadastrar(Lancamento newDto) {
		validarLancamento(newDto);
		return super.cadastrar(newDto);
	}

	@Override
	public Lancamento atualizarPorId(Long id, Lancamento updateDto) {
		validarLancamento(updateDto);
		return super.atualizarPorId(id, updateDto);
	}

	public Page<Lancamento> filtar(LancamentoFilter filter, Pageable pageable) {
		return repository.filtrar(filter, pageable);
	}

	public Page<LancamentoDTO> filtarDTO(LancamentoFilter filter, Pageable pageable) {
		return repository.filtrarDTO(filter, pageable);
	}

	public List<LancamentoEstatisticaCategoriaDTO> filtrarPorCategoria(LocalDate dataReferencia) {
		return repository.filtrarPorCategoria(dataReferencia);
	}

	// Executa o método 5 segundos após o fim da execução anterior:
	// @Scheduled(fixedDelay = 1000 * 30)
	@Scheduled(cron = "0 0 0 * * *")
	private void avisarSobreLancamentosVencidos() {
		List<Lancamento> vencidos = repository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
		List<Usuario> destinatarios = usuarioRepository.findByPermissoesDescricao("ROLE_PESQUISAR_LANCAMENTO");
		if (!vencidos.isEmpty() && !destinatarios.isEmpty()) {
			mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);
		}
	}

	// TODO: Melhorar a validação das entidades associadas, implementá-la na
	// abstractService.
	private void validarLancamento(Lancamento lancamento) {
		Long idPessoa = null;
		try {
			idPessoa = lancamento.getPessoa().getId();
			Pessoa pessoa = pessoaService.obterPorId(idPessoa);
			if (!pessoa.getStatus()) {
				throw new PessoaInativaException(pessoa.getNome(), idPessoa);
			}
		} catch (RecursoNaoEncontradoException e) {
			throw new RecursoAssociadoNaoEncontradoException(classeModel.getSimpleName(), Pessoa.class.getSimpleName(),
					idPessoa);
		}

		Long idCategoria = null;
		try {
			idCategoria = lancamento.getCategoria().getId();
			categoriaService.obterPorId(idCategoria);
		} catch (RecursoNaoEncontradoException e) {
			throw new RecursoAssociadoNaoEncontradoException(classeModel.getSimpleName(),
					Categoria.class.getSimpleName(), idCategoria);
		}
	}

}
