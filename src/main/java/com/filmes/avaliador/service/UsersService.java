package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NoContentException;
import com.filmes.avaliador.model.Users;
import com.filmes.avaliador.repository.UsersRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    public List<Users> listarUsuarios(String nome){
        List<Users> usuarios = repository.findUsersByNomeLike(nome);

        if(usuarios.isEmpty()){
            throw new NoContentException("Nenhum usuário encontrado");
        }

        return usuarios;
    }

    public void cadastrarUsuario(Users usuario){

        Optional<Users> usuarioPossivel = repository.findByEmail(usuario.getEmail());

        if(usuarioPossivel.isPresent()){
            throw new ConflitoException("Usuário já cadastrado");
        }

        repository.save(usuario);
    }
}
