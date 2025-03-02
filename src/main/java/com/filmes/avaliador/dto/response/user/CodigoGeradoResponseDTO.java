package com.filmes.avaliador.dto.response.user;

import lombok.Builder;

@Builder
public record CodigoGeradoResponseDTO(
        String mensagem,
        Integer indice
) {
}
