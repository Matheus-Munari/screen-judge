package com.filmes.avaliador.mapper;

import com.filmes.avaliador.dto.request.FilmeRequestDTO;
import com.filmes.avaliador.dto.response.filme.FilmePorIdResponseDTO;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.filme.FilmePorIdTmdbResponseDTO;
import com.filmes.avaliador.model.Filme;

public class FilmeMapper {


    public static Filme toEntity(FilmeRequestDTO dto){
        return Filme.builder()
                .titulo(dto.titulo())
                .tituloOriginal(dto.tituloOriginal())
                .diretor(dto.diretor())
                .dataLancamento(dto.dataLancamento())
                .generoPrincipal(dto.generoPrincipal())
                .posterPath(dto.posterPath())
                .idTmdb(dto.idTmdb())
                .overview(dto.overview())
                .tagLine(dto.tagLine())
                .trailerKey(dto.trailerKey())
                .plataformaTrailer(dto.plataformaTrailer())
                .build();
    }

    public static FilmeResponseDTO toFilmeResponseDTO(Filme entity){
        return FilmeResponseDTO.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .datalancamento(entity.getDataLancamento())
                .diretor(entity.getDiretor())
                .genero(entity.getGeneroPrincipal())
                .posterPath(entity.getPosterPath())
                .build();

    }

    public static FilmePorIdResponseDTO toFilmePorIdResponseDTO(FilmePorIdTmdbResponseDTO tmdb, String videoKey, String plataforma, String diretor){
        return FilmePorIdResponseDTO.builder()
                .id(tmdb.id())
                .dataLancamento(tmdb.dataLancamento())
                .generoPrincipal(tmdb.generos().get(0).name())
                .title(tmdb.title())
                .idTmdb(tmdb.id())
                .posterPath(tmdb.posterPath())
                .overview(tmdb.overview())
                .tagLine(tmdb.tagLine())
                .tituloOriginal(tmdb.tituloOriginal())
                .diretor(diretor)
                .trailerKey(videoKey)
                .plataformaTrailer(plataforma)
                .build();
    }
}
