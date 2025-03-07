package com.filmes.avaliador.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;
import java.time.Year;

@Builder
public record FilmeRequestDTO(
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 250, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 250)")
        String tituloOriginal,
        @NotBlank
        String idImdb,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 1_000, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 1000)")
        String overview,
        String tagLine,
        @NotBlank(message = "Campo obrigatório")
        String trailerKey,
        @NotBlank(message = "Campo obrigatório")
        String plataformaTrailer,
        @NotBlank(message = "Campo obrigatório")
        @Size(max = 250, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 250)")
        String titulo,
        @Size(max = 150, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 150)")
        @NotBlank(message = "Campo obrigatório")
        String diretor,
        @NotNull(message = "Campo obrigatório")
        @PastOrPresent(message = "O filme deve ter sido lançado para ser cadastrado")
        LocalDate dataLancamento,
        @Size(max = 100, min = 2, message = "Quantidade de caracteres fora do limite (mínimo 2, máximo 100)")
        @NotBlank(message = "Campo obrigatório")
        String generoPrincipal,
        @NotBlank(message = "Campo obrigatório")
        String posterPath
) {
}
