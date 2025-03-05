package com.filmes.avaliador.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ListaRecomendacoesCadastroDTO(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotBlank(message = "Campo obrigatório")
        String genero,
        @NotBlank(message = "Campo obrigatório")
        String idUsuario
) {
}
