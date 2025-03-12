package com.filmes.avaliador.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ComentarioRequestDTO(
        @NotBlank(message = "Campo obrigatório")
        String comentario,
        @NotBlank(message = "Campo obrigatório")
        String idUsuario
) {
}
