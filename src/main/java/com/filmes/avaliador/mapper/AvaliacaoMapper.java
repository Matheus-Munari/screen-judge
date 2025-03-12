package com.filmes.avaliador.mapper;

import com.filmes.avaliador.dto.request.AvaliacaoDTO;
import com.filmes.avaliador.dto.response.avaliacao.AvaliacaoUsuarioFilmeResponseDTO;
import com.filmes.avaliador.dto.response.avaliacao.ComentariosAvaliacaoDTO;
import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.model.Comentario;
import com.filmes.avaliador.model.ComentarioAvaliacao;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.user.Users;

import java.util.UUID;

public class AvaliacaoMapper {

    public static Avaliacao toEntity(AvaliacaoDTO dto){
        Users user = new Users();
        user.setId(UUID.fromString(dto.idUsuario()));

        Filme filme = new Filme();
        filme.setId(dto.idFilme());

        return Avaliacao.builder()
                .nota(dto.nota())
                .comentario(dto.comentario())
                .usuario(user)
                .filme(filme)
                .build();
    }

    public static AvaliacaoUsuarioFilmeResponseDTO toAvaliacaoUsuarioFilmeDto(Avaliacao entity){
        return AvaliacaoUsuarioFilmeResponseDTO.builder()
                .id(entity.getId())
                .nota(entity.getNota())
                .comentario(entity.getComentario())
                .usuario(UserMapper.toResponseDTO(entity.getUsuario()))
                .filme(FilmeMapper.toFilmeResponseDTO(entity.getFilme()))
                .build();
    }

    public static ComentariosAvaliacaoDTO toComentariosAvaliacaoDto(ComentarioAvaliacao entity){
        Comentario comentario = entity.getComentario();
        return ComentariosAvaliacaoDTO.builder()
                .id(comentario.getId())
                .comentario(comentario.getComentario())
                .usuario(UserMapper.toResponseDTO(comentario.getUsuario()))
                .dataComentario(comentario.getDataComentario())
                .build();
    }

}
