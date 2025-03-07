package com.filmes.avaliador.dto.response.filme;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FilmePorIdResponseDTO(
        Long id,
        String tituloOriginal,
        String idImdb,
        String overview,
        String posterPath,
        LocalDate dataLancamento,
        String tagLine,
        String title,
        String generoPrincipal,
        String diretor,
        String trailerKey,
        String plataformaTrailer
) {
}
