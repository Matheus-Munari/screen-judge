package com.filmes.avaliador.dto.response.tmdb.trailler;

import java.util.List;

public record TmdbTraillerResponseDTO(

        List<TraillerResponseDTO> results

) {
}
