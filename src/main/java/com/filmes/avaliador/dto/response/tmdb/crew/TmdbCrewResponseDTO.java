package com.filmes.avaliador.dto.response.tmdb.crew;

import java.util.List;

public record TmdbCrewResponseDTO(
        List<CrewResponseDTO> crew
) {
}
