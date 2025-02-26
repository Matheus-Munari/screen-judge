package com.filmes.avaliador.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClientSaveDTO(
        @NotBlank(message = "Campo obrigatório")
        String clientId,
        @NotBlank(message = "Campo obrigatório")
        String clientSecret,
        @NotBlank(message = "Campo obrigatório")
        String redirectURI,
        @NotBlank(message = "Campo obrigatório")
        String scope
) {
}
