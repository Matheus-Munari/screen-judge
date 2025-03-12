package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.ComentarioAvaliacao;
import com.filmes.avaliador.model.ListaRecomendacoesFilme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ComentarioAvaliacaoRepository extends JpaRepository<ComentarioAvaliacao, Integer>, JpaSpecificationExecutor<ComentarioAvaliacao> {

    Optional<ComentarioAvaliacao> findByComentarioId(Integer idComentario);

}
