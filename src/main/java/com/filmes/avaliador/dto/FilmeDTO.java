package com.filmes.avaliador.dto;

import lombok.Builder;
import java.time.Year;

@Builder
public record FilmeDTO(
        String titulo,
        String diretor,
        Year anoLancamento,
        String genero,
        String urlImagem
) {
}
