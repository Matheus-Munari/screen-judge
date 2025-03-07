package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme, Long>, JpaSpecificationExecutor<Filme> {

    Optional<Filme> findByTituloAndDiretorAndDataLancamento(String titulo, String Diretor, LocalDate anoLancamento);

}
