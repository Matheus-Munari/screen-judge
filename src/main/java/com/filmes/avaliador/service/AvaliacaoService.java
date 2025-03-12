package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.model.Comentario;
import com.filmes.avaliador.model.ComentarioAvaliacao;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.user.Users;
import com.filmes.avaliador.repository.AvaliacaoRepository;
import com.filmes.avaliador.repository.ComentarioAvaliacaoRepository;
import com.filmes.avaliador.repository.ComentarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository repository;

    private final UsersService userService;

    private final FilmeService filmeService;

    public Avaliacao cadastrarNovaAvaliacao(Avaliacao avaliacao){

        Users usuario = userService.usuarioPorId(avaliacao.getUsuario().getId());
        Filme filme = filmeService.buscarPorId(avaliacao.getFilme().getId());

        boolean usuariojaAvaliou = repository.existsByFilmeAndUsers(filme, usuario);

        if(usuariojaAvaliou){
            throw new ConflitoException("Uma avaliação já foi cadastrada para este filme");
        }

        avaliacao.setUsuario(usuario);
        avaliacao.setFilme(filme);
        avaliacao.setDataAvaliado(LocalDate.now());
        avaliacao.setDataAtualizado(LocalDate.now());

        return repository.save(avaliacao);
    }

    public List<Avaliacao> buscarTodas(){
        return repository.findAll();
    }

    @Transactional
    public void deletar(Integer id){

        Optional<Avaliacao> avaliacao = repository.findById(id);

        if(avaliacao.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repository.delete(avaliacao.get());

    }

    public void atualizar(Avaliacao avaliacao){

        Optional<Avaliacao> avaliacaoBuscada = repository.findById(avaliacao.getId());

        if(avaliacaoBuscada.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repository.save(avaliacao);

    }

    public Avaliacao buscarPorId(Integer id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
