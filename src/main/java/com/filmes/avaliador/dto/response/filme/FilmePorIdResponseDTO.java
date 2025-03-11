package com.filmes.avaliador.dto.response.filme;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record FilmePorIdResponseDTO(
        Long id,
        String tituloOriginal,
        Long idTmdb,
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
