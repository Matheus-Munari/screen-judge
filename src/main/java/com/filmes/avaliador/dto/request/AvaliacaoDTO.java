package com.filmes.avaliador.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AvaliacaoDTO(
        @NotNull(message = "Campo obrigatório")
        Double nota,
        @NotBlank(message = "Campo obrigatório")
        String comentario,
        @NotBlank(message = "Campo obrigatório")
        String idUsuario,
        @NotNull(message = "Campo obrigatório")
        Integer idFilme
) {
}
