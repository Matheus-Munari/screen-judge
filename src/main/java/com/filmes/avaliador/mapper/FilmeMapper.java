package com.filmes.avaliador.mapper;

import com.filmes.avaliador.dto.request.FilmeDTO;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import com.filmes.avaliador.model.Filme;

public class FilmeMapper {


    public static Filme toEntity(FilmeDTO dto){
        return Filme.builder()
                .titulo(dto.titulo())
                .diretor(dto.diretor())
                .anoLancamento(dto.anoLancamento())
                .genero(dto.genero())
                .urlImagem(dto.urlImagem())
                .build();
    }

    public static FilmeResponseDTO toFilmeResponseDTO(Filme entity){
        return FilmeResponseDTO.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .anoLancamento(entity.getAnoLancamento())
                .diretor(entity.getDiretor())
                .genero(entity.getGenero())
                .urlImagem(entity.getUrlImagem())
                .build();

    }
}
