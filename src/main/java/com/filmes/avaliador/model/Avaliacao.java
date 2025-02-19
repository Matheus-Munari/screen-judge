package com.filmes.avaliador.model;

import com.filmes.avaliador.model.user.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double nota;

    private String comentario;

    @ManyToOne()
    @JoinColumn(name = "id_usuario")
    private Users usuario;

    @ManyToOne()
    @JoinColumn(name = "id_filme")
    private Filme filme;

    private LocalDate dataAvaliado;

    private LocalDate dataAtualizado;

}
