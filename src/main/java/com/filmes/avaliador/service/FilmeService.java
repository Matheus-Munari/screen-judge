package com.filmes.avaliador.service;

import com.filmes.avaliador.clients.TmdbClient;
import com.filmes.avaliador.config.TmdbClientConfig;
import com.filmes.avaliador.dto.response.tmdb.TmdbResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.crew.CrewResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.crew.TmdbCrewResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.trailler.TraillerResponseDTO;
import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import static com.filmes.avaliador.repository.specs.FilmeSpecs.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository repository;

    private final TmdbClient tmdbClient;

    private final TmdbClientConfig tmdbClientConfig;

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

    public Page<Filme> buscarFilmes(String titulo,
                                    String diretor,
                                    Year anoLancamento,
                                    String genero,
                                    Integer pagina,
                                    Integer tamanhoPagina){

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

        Pageable page = PageRequest.of(pagina, tamanhoPagina);

        Example<Filme> filmeExample = Example.of(filme, matcher);
        return repository.findAll(filmeExample, page);
    }

    public Page<Filme> buscarFilmesSpecs(String titulo,
                                         String diretor,
                                         Year anoLancamento,
                                         String genero,
                                         Integer pagina,
                                         Integer tamanhoPagina){
        Specification<Filme> specs = Specification.where((root, query, cb) -> cb.conjunction() );

        if(titulo != null){
            specs = specs.and(tituloLike(titulo));
        }
        if(diretor != null){
            specs = specs.and(diretorLike(diretor));
        }
        if(anoLancamento != null){
            specs = specs.and(anoLancamentoEqual(anoLancamento));
        }
        if(genero != null){
            specs = specs.and(generoEqual(genero));
        }

        Pageable page = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, page);

    }

    public Filme buscarPorId(Integer id){

        Optional<Filme> filme = repository.findById(id);

        if(filme.isEmpty()){
            throw new NotFoundException("Filme não encontrado");
        }

        return filme.get();
    }

    public TmdbResponseDTO buscarFilmesTmdb(String filme, Boolean incluirAdulto, Integer pagina){
        return tmdbClient.buscarFilmes(filme, "pt-BR", pagina, incluirAdulto, tmdbClientConfig.getKey());
    }

    public String buscarDiretor(Long idFilme){
        List<CrewResponseDTO> crew = tmdbClient.buscarCreditos(idFilme, "pt-BR", tmdbClientConfig.getKey()).crew();

        for(CrewResponseDTO daVez : crew){
            if(daVez.job().equals("Director")){
                return daVez.name();
            }
        }
        return null;
    }

    public TraillerResponseDTO buscarTrailer(Long idFilme){

        List<TraillerResponseDTO> trailer = tmdbClient.buscarTraillers(idFilme, "pt-BR", tmdbClientConfig.getKey()).results();

        for(TraillerResponseDTO video : trailer){
            if(video.type().equals("Trailer")){
                return video;
            }
        }

        trailer = tmdbClient.buscarTraillers(idFilme, "en-US", tmdbClientConfig.getKey()).results();

        for(TraillerResponseDTO video : trailer){
            if(video.type().equals("Trailer")){
                return video;
            }
        }

        return null;
    }


}
