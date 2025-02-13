package com.filmes.avaliador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String nome;

    private String email;

    private String senha;

    private LocalDate dataNascimento;

    private LocalDate dataCriada;

    private LocalDate dataAtualizada;

    private Boolean ativo;
}
