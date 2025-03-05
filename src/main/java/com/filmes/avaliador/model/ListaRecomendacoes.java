package com.filmes.avaliador.model;


import com.filmes.avaliador.model.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListaRecomendacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String genero;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Users usuario;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

}
