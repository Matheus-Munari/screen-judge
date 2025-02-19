package com.filmes.avaliador.repository;

import com.filmes.avaliador.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    List<Users> findUsersByNomeLike(String nome);

    UserDetails findByEmail(String email);
}
