package com.filmes.avaliador.dto.response.tmdb.crew;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
public record CrewResponseDTO(
        String name,
        String job
) {
}
