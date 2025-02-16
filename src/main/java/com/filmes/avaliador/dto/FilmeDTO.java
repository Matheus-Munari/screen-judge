package com.filmes.avaliador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.time.Year;

@Builder
public record FilmeDTO(
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 250, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 250)")
        String titulo,
        @Size(max = 150, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 150)")
        @NotBlank(message = "Campo obrigatório")
        String diretor,
        @NotNull(message = "Campo obrigatório")
        @PastOrPresent(message = "O filme deve ter sido lançado para ser cadastrado")
        Year anoLancamento,
        @Size(max = 100, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 100)")
        @NotBlank(message = "Campo obrigatório")
        String genero,
        @NotBlank(message = "Campo obrigatório")
        String urlImagem
) {
}
