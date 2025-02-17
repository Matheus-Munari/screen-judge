package com.filmes.avaliador.mapper;

import com.filmes.avaliador.dto.request.FilmeDTO;
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

}
