package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.ListaRecomendacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ListaRecomendacoesRepository extends JpaRepository<ListaRecomendacoes, Long>, JpaSpecificationExecutor<ListaRecomendacoes> {

    Optional<ListaRecomendacoes> findByNome(String nome);

}
