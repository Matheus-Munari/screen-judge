package com.filmes.avaliador.dto.response.tmdb.filme;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filmes.avaliador.dto.response.tmdb.crew.TmdbCrewResponseDTO;

import java.time.LocalDate;
import java.util.List;

public record FilmePorIdTmdbResponseDTO(
        Long id,
        @JsonProperty("original_title")
        String tituloOriginal,
        String overview,
        @JsonProperty("poster_path")
        String posterPath,
        @JsonProperty("release_date")
        LocalDate dataLancamento,
        @JsonProperty("tagline")
        String tagLine,
        String title,
        @JsonProperty("genres")
        List<GenerosTmdbResponseDTO> generos,
        TmdbCrewResponseDTO credits
) {
}
