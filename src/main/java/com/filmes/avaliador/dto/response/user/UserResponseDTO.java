package com.filmes.avaliador.dto.response.user;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record UserResponseDTO(
        UUID id,
        String nome,
        String email,
        LocalDate dataNascimento,
        boolean ativo
) {
}
