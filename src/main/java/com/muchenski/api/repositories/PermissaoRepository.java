package com.muchenski.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.muchenski.api.domain.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

}
