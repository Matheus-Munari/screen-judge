package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Comentario;
import com.filmes.avaliador.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    Optional<Comentario> findByUsuario(Users usuario);
}
