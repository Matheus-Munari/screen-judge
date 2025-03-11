package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.ListaRecomendacoes;
import com.filmes.avaliador.model.ListaRecomendacoesFilme;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ListaRecomandacoesFilmeRepository extends JpaRepository<ListaRecomendacoesFilme, Long>, JpaSpecificationExecutor<ListaRecomendacoesFilme>{
    
    Optional<ListaRecomendacoesFilme> findByListaRecomendacoesIdAndFilmeId(Long listaRecomendacoesId, Long filmeId);

    Page<ListaRecomendacoesFilme> findAllByListaRecomendacoesId(Long listaRecomendacoesId, org.springframework.data.domain.Pageable pageable);
}
