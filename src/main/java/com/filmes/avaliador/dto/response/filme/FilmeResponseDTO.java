package com.filmes.avaliador.dto.response.filme;

import lombok.Builder;

import java.time.LocalDate;
import java.time.Year;

@Builder
public record FilmeResponseDTO(
        Integer id,
        String titulo,
        String diretor,
        Year anoLancamento,
        String genero,
        String urlImagem
) {
}
