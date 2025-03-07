package com.filmes.avaliador.dto.response.filme;

import lombok.Builder;

import java.time.LocalDate;
import java.time.Year;

@Builder
public record FilmeResponseDTO(
        Long id,
        String titulo,
        String diretor,
        LocalDate datalancamento,
        String genero,
        String posterPath
) {
}
