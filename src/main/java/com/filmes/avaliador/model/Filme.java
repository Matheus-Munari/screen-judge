package com.filmes.avaliador.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String tituloOriginal;

    private String diretor;

    private LocalDate dataLancamento;

    private String generoPrincipal;

    private String posterPath;

    private String idImdb;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private String tagLine;

    private String trailerKey;

    private String plataformaTrailer;

    private LocalDate dataCriada;

    private LocalDate dataAlterada;
}
