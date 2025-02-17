package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.Users;
import com.filmes.avaliador.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AvaliacaoService {

    private AvaliacaoRepository repository;

    private UsersService userService;

    private FilmeService filmeService;

    public AvaliacaoService(AvaliacaoRepository repository, UsersService userService, FilmeService filmeService) {
        this.repository = repository;
        this.userService = userService;
        this.filmeService = filmeService;
    }

    public void cadastrarNovaAvaliacao(Avaliacao avaliacao){

        Users usuario = userService.usuarioPorId(avaliacao.getUsuario().getId());
        Filme filme = filmeService.buscarPorId(avaliacao.getId());

        boolean usuariojaAvaliou = repository.existsByFilmeAndUsers(filme, usuario);

        if(usuariojaAvaliou){
            throw new ConflitoException("Uma avaliação já foi cadastrada para este filme");
        }

        avaliacao.setUsuario(usuario);
        avaliacao.setFilme(filme);
        avaliacao.setDataAvaliado(LocalDate.now());
        avaliacao.setDataAtualizado(LocalDate.now());

        repository.save(avaliacao);
    }
}
