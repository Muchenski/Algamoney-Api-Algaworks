package com.muchenski.api.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muchenski.api.domain.Pessoa;
import com.muchenski.api.resources.exceptions.ParametroDeRequisicaoInvalidoException;
import com.muchenski.api.resources.utils.ViaCepUtils;
import com.muchenski.api.services.PessoaService;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource extends AbstractResource<Pessoa, Long, PessoaService> {

	public PessoaResource(PessoaService service) {
		super(service);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> cadastrar(@Valid @RequestBody Pessoa newDto, HttpServletResponse response) {
		ViaCepUtils.setarEnderecoViaCEP(newDto);
		return super.cadastrar(newDto, response);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	@Override
	public ResponseEntity<?> obterPorId(@PathVariable Long id) {
		return super.obterPorId(id);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	@Override
	public ResponseEntity<?> obterTodos() {
		return super.obterTodos();
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @Valid @RequestBody Pessoa updateDto) {
		ViaCepUtils.setarEnderecoViaCEP(updateDto);
		return super.atualizarPorId(id, updateDto);
	}

	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> removerPorId(@PathVariable Long id) {
		return super.removerPorId(id);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@PutMapping(value = "/{id}/alterarStatus")
	public ResponseEntity<?> ativarOuInativar(@PathVariable Long id) {
		return ResponseEntity.ok(service.alterarStatus(id));
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	@GetMapping(params = "nome")
	public ResponseEntity<?> obterPorNome(@RequestParam(name = "nome", defaultValue = "") String nomeFilter,
			Pageable pageable) {
		if (nomeFilter.length() > 30) {
			throw new ParametroDeRequisicaoInvalidoException("O parâmetro 'nome' deve ter no máximo 30 caracteres!");
		}
		Page<Pessoa> pessoas = service.obterPorNome(nomeFilter, pageable);
		return pessoas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(pessoas);
	}
}
