package com.filmes.avaliador.dto.response.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FilmeTmdbDTO(

    Long id,
    @JsonProperty("poster_path")
    String posterPath,
    String title

) {

}
