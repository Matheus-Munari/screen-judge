package com.filmes.avaliador.dto.response.avaliacao;

import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import com.filmes.avaliador.dto.response.user.UserResponseDTO;
import lombok.Builder;

@Builder
public record AvaliacaoUsuarioFilmeResponseDTO(
        Integer id,
        Double nota,
        String comentario,
        UserResponseDTO usuario,
        FilmeResponseDTO filme
) {
}
