package com.filmes.avaliador.dto.response.avaliacao;

import com.filmes.avaliador.dto.response.user.UserResponseDTO;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ComentariosAvaliacaoDTO(
        Integer id,
        String comentario,
        UserResponseDTO usuario,
        LocalDateTime dataComentario
) {
}
