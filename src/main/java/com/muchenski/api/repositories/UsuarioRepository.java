package com.muchenski.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muchenski.api.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public abstract Optional<Usuario> findByEmail(String email);
	
	public abstract List<Usuario> findByPermissoesDescricao(String descricao);
}
