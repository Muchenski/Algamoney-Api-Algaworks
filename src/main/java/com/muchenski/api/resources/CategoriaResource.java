package com.muchenski.api.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muchenski.api.domain.Categoria;
import com.muchenski.api.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource extends AbstractResource<Categoria, Long, CategoriaService> {

	public CategoriaResource(CategoriaService service) {
		super(service);
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> cadastrar(@Valid @RequestBody Categoria newDto, HttpServletResponse response) {
		return super.cadastrar(newDto, response);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	@Override
	public ResponseEntity<?> obterPorId(@PathVariable Long id) {
		return super.obterPorId(id);
	}

	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	@Override
	public ResponseEntity<?> obterTodos() {
		return super.obterTodos();
	}

	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> atualizarPorId(@PathVariable Long id, @Valid @RequestBody Categoria updateDto) {
		return super.atualizarPorId(id, updateDto);
	}

	@PreAuthorize("hasAuthority('ROLE_REMOVER_CATEGORIA') and #oauth2.hasScope('write')")
	@Override
	public ResponseEntity<?> removerPorId(@PathVariable Long id) {
		return super.removerPorId(id);
	}
}
