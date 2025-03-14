package com.filmes.avaliador.dto.response.email;

import com.filmes.avaliador.dto.response.avaliacao.AvaliacaoUsuarioFilmeResponseDTO;
import com.filmes.avaliador.dto.response.avaliacao.ComentariosAvaliacaoDTO;
import com.filmes.avaliador.dto.response.user.UserResponseDTO;
import com.filmes.avaliador.model.Comentario;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ComentarioAvaliacaoEmailDTO(
        Integer id,
        ComentariosAvaliacaoDTO comentario,
        AvaliacaoUsuarioFilmeResponseDTO avaliacao,
        LocalDateTime dataComentario
) {
}
