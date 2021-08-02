package com.muchenski.api.resources;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muchenski.api.domain.Lancamento;
import com.muchenski.api.dtos.LancamentoDTO;
import com.muchenski.api.dtos.LancamentoEstatisticaCategoriaDTO;
import com.muchenski.api.repositories.filters.LancamentoFilter;
import com.muchenski.api.services.LancamentoService;
import com.zaxxer.hikari.HikariDataSource;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource extends AbstractResource<Lancamento, Long, LancamentoService> {

	@Autowired
	private HikariDataSource dataSource;
	
	public LancamentoResource(LancamentoService service) {
		super(service);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> cadastrar(@Valid @RequestBody Lancamento newDto, HttpServletResponse response) {
		return super.cadastrar(newDto, response);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@Override
	public ResponseEntity<?> obterPorId(@PathVariable Long id) {
		return super.obterPorId(id);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@Override
	public ResponseEntity<?> obterTodos() {
		return super.obterTodos();
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @Valid @RequestBody Lancamento updateDto) {
		return super.atualizarPorId(id, updateDto);
	}

	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> removerPorId(@PathVariable Long id) {
		return super.removerPorId(id);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@GetMapping(value = "/filtrar")
	public ResponseEntity<?> filtrar(LancamentoFilter filter, Pageable pageable) {
		Page<Lancamento> lancamentos = service.filtar(filter, pageable);
		return lancamentos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lancamentos);
	}

	// Possui o mesmo path de filtrar, porém se tiver o parâmetro "resumo", este será o
	// método chamado.
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@GetMapping(value = "/filtrar", params = "resumo")
	public ResponseEntity<?> filtrarDTO(LancamentoFilter filter, Pageable pageable) {
		Page<LancamentoDTO> lancamentos = service.filtarDTO(filter, pageable);
		return lancamentos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lancamentos);
	}
	
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	@GetMapping(value = "/estatistica/filtrarPorCategoria")
	public ResponseEntity<?> filtrarPorCategoria(
			@RequestParam(required = false) Integer mesReferencia, 
			@RequestParam(required = false) Integer anoReferencia
		) {
		LocalDate dataReferencia = null;
		
		String dataSourceName = dataSource.getDriverClassName();
		
		Integer maxYear = dataSourceName.equalsIgnoreCase("org.postgresql.Driver") ? 5874897 : LocalDate.MAX.getYear();
		
		boolean mesReferenciaValido = mesReferencia != null && mesReferencia >= LocalDate.MIN.getMonthValue() && mesReferencia <= LocalDate.MAX.getMonthValue();
		boolean anoReferenciaValido = anoReferencia != null && anoReferencia >= LocalDate.MIN.getYear() && anoReferencia <= maxYear;
		if(mesReferenciaValido && anoReferenciaValido) {
			dataReferencia = LocalDate.of(anoReferencia, mesReferencia, 1);
		} else {
			dataReferencia = LocalDate.now();
		}
		List<LancamentoEstatisticaCategoriaDTO> lancamentos = service.filtrarPorCategoria(dataReferencia);
		return lancamentos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lancamentos);
	}
}
