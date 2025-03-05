package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.ListaRecomendacoes;
import com.filmes.avaliador.repository.ListaRecomendacoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import static com.filmes.avaliador.repository.specs.ListaRecomendacoesSpecs.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListaRecomendacoesService {

    private final ListaRecomendacoesRepository repository;

    public ListaRecomendacoes cadastrar(ListaRecomendacoes listaRecomendacoes){

        Optional<ListaRecomendacoes> listaRecomendacoesOptional = repository.findByNome(listaRecomendacoes.getNome());

        if(listaRecomendacoesOptional.isPresent()){
            throw new ConflitoException("Lista de recomendação ja criada com este nome");
        }

        LocalDateTime timeStampHoje = LocalDateTime.now();

        listaRecomendacoes.setDataAtualizacao(timeStampHoje);
        listaRecomendacoes.setDataCriacao(timeStampHoje);

        return repository.save(listaRecomendacoes);

    }

    public void deletar(Long id){

        Optional<ListaRecomendacoes> listaRecomendacoesOptional = repository.findById(id);

        if(listaRecomendacoesOptional.isEmpty()){
            throw new NotFoundException("Lista não encontrada");
        }

        repository.delete(listaRecomendacoesOptional.get());

    }

    public ListaRecomendacoes atualizar(ListaRecomendacoes listaRecomendacoes, Long id){
        Optional<ListaRecomendacoes> listaRecomendacoesOptional = repository.findById(id);

        if(listaRecomendacoesOptional.isEmpty()){
            throw new NotFoundException("Lista não encontrada");
        }

        listaRecomendacoesOptional = repository.findByNome(listaRecomendacoes.getNome());

        if(listaRecomendacoesOptional.isPresent()){
            throw new ConflitoException("Lista já cadastrada com este nome");
        }

        listaRecomendacoes.setId(id);

        return repository.save(listaRecomendacoes);
    }

    public Page<ListaRecomendacoes> buscar(
            String nomeUsuario,
            String genero,
            String nome,
            Integer tamanhoPagina,
            Integer pagina
    ){

        Specification<ListaRecomendacoes> specs = Specification.where(((root, query, cb) -> cb.conjunction()));

        if(nomeUsuario != null){
            specs = specs.and(nomeUsuarioLike(nomeUsuario));
        }

        if(nome != null){
            specs = specs.and(nomeLike(nome));
        }

        if(genero != null){
            specs = specs.and(generoEquals(genero));
        }

        Pageable page = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, page);

    }



}
