package com.filmes.avaliador.dto.response.tmdb.crew;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TmdbCrewResponseDTO(
        @JsonProperty("crew")
        List<CrewResponseDTO> crew
) {
}
