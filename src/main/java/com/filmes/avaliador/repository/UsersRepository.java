package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    List<Users> findUsersByNomeLike(String nome);

    Optional<Users> findByEmail(String email);
}
