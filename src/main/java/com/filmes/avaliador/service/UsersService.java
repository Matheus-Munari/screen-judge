package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NoContentException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.Users;
import com.filmes.avaliador.repository.UsersRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    public List<Users> listarUsuarios(String nome){

        Users users = new Users();
        users.setNome(nome);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Users> usersExample = Example.of(users, matcher);
        return repository.findAll(usersExample);
    }

    public void cadastrarUsuario(Users usuario){

        Optional<Users> usuarioPossivel = repository.findByEmail(usuario.getEmail());

        if(usuarioPossivel.isPresent()){
            throw new ConflitoException("Usuário já cadastrado");
        }

        usuario.setAtivo(true);
        usuario.setDataAtualizada(LocalDate.now());
        usuario.setDataCriada(LocalDate.now());

        repository.save(usuario);
    }

    public void atualizarUsuario(Users user, UUID id){

        Optional<Users> usuarioCadastrado = repository.findById(id);

        if(usuarioCadastrado.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }

        user.setId(id);

        repository.save(user);
    }

    @Transactional
    public void deletarUsuario(UUID id){

        Optional<Users> usuarioADeletar = repository.findById(id);

        if(usuarioADeletar.isEmpty()){
            throw new NotFoundException("Usuário não cadastrado");
        }

        if(!usuarioADeletar.get().getAtivo()){
            throw new ConflitoException("Usuário já deletado");
        }

        usuarioADeletar.get().setAtivo(false);

        repository.save(usuarioADeletar.get());
    }

    public Users usuarioPorId(UUID id){

        Optional<Users> usuarioBuscado = repository.findById(id);

        if(usuarioBuscado.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }

        return usuarioBuscado.get();

    }
}
