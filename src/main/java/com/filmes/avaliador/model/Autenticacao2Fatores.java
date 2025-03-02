package com.filmes.avaliador.model;

import com.filmes.avaliador.model.user.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Autenticacao2Fatores {

    @Id
    @GeneratedValue
    private Integer id;

    private String codigo;

}
