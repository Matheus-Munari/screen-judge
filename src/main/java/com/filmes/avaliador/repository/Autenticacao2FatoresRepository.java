package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Autenticacao2Fatores;
import com.filmes.avaliador.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface Autenticacao2FatoresRepository extends JpaRepository<Autenticacao2Fatores,Integer> {
    Optional<Autenticacao2Fatores> findByCodigo(String codigo);
}
