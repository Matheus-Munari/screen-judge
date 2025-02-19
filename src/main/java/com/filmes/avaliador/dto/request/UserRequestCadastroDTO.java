package com.filmes.avaliador.dto.request;

import com.filmes.avaliador.handler.exception.validator.EnumValid;
import com.filmes.avaliador.model.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

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
        LocalDate dataNascimento,
        @NotNull(message = "Campo obrigatório")
        @EnumValid(enumClass = UserRole.class, message = "Role inválida. Valores aceitos: ADMIN, USER")
        String role
) {
}
