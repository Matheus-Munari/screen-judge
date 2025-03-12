package com.filmes.avaliador.model;

import com.filmes.avaliador.model.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comentario;

    @ManyToOne()
    @JoinColumn(name = "id_usuario_comentario")
    private Users usuario;

    private LocalDateTime dataComentario;

}
