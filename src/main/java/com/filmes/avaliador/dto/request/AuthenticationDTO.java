package com.filmes.avaliador.dto.request;

public record AuthenticationDTO(
        String email,
        String senha
) {
}
