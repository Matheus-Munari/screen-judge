package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.Users;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    @Query("select COUNT(a) > 0 from Avaliacao as a join a.usuario join a.filme where a.filme = :filme and a.usuario = :usuario")
    boolean existsByFilmeAndUsers(
            @Param("filme") Filme filme,
            @Param("usuario") Users usuario);

}
