package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NoContentException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.repository.FilmeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    private FilmeRepository repository;

    public FilmeService(FilmeRepository repository) {
        this.repository = repository;
    }

    public Filme cadastrarFilme(Filme filme){

        Optional<Filme> filmeBanco = repository.findByTituloAndDiretorAndAnoLancamento(filme.getTitulo(), filme.getDiretor(), filme.getAnoLancamento());

        if(filmeBanco.isPresent()){
            throw new ConflitoException("Filme já cadastrado");
        }

        filme.setDataCriada(LocalDate.now());
        filme.setDataAlterada(LocalDate.now());

        return repository.save(filme);

    }

    public void atualizarFilme(Filme filme, Integer id){

        Optional<Filme> filmeAAtualizar = repository.findById(id);

        if(filmeAAtualizar.isEmpty()){
            throw new NotFoundException("Filme não encontrado");
        }

        filme.setId(id);
        filme.setDataAlterada(LocalDate.now());

        repository.save(filme);
    }

    public void deletarFilme(Integer id){

        Optional<Filme> filmeADeletar = repository.findById(id);

        if(filmeADeletar.isEmpty()){
            throw new NotFoundException("Filme não encontrado");
        }

        repository.deleteById(id);

    }

    public List<Filme> buscarFilmes(String titulo,
                                    String diretor,
                                    Year anoLancamento,
                                    String genero){

        Filme filme = new Filme();
        filme.setGenero(genero);
        filme.setDiretor(diretor);
        filme.setTitulo(titulo);
        filme.setAnoLancamento(anoLancamento);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Filme> filmeExample = Example.of(filme, matcher);
        return repository.findAll(filmeExample);
    }

    public Filme buscarPorId(Integer id){

        Optional<Filme> filme = repository.findById(id);

        if(filme.isEmpty()){
            throw new NotFoundException("Filme não encontrado");
        }

        return filme.get();
    }


}
