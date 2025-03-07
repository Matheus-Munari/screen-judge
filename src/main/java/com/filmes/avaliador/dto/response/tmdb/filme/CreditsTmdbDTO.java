package com.filmes.avaliador.dto.response.tmdb.filme;

import com.filmes.avaliador.dto.response.tmdb.crew.TmdbCrewResponseDTO;

public record CreditsTmdbDTO(
        TmdbCrewResponseDTO crew
) {
}
