package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme, Integer> {

    Optional<Filme> findByTituloAndDiretorAndAnoLancamento(String titulo, String Diretor, Year anoLancamento);

}
