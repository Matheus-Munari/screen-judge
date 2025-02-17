package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.Year;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme, Integer>, JpaSpecificationExecutor<Filme> {

    Optional<Filme> findByTituloAndDiretorAndAnoLancamento(String titulo, String Diretor, Year anoLancamento);

}
