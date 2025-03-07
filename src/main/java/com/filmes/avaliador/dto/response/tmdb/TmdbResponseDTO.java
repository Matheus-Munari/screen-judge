package com.filmes.avaliador.dto.response.tmdb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record TmdbResponseDTO(

        @JsonProperty("results")
        List<FilmeTmdbDTO> filmes

) {

}
