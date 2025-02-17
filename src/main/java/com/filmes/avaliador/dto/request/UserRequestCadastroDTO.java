package com.filmes.avaliador.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserRequestCadastroDTO(
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotBlank(message = "Campo obrigatório")
        @Email
        String email,
        @NotBlank(message = "Campo obrigatório")
        String senha,
        @NotNull(message = "Campo obrigatório")
        LocalDate dataNascimento
) {
}
