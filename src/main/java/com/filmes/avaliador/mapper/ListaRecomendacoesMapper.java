package com.filmes.avaliador.mapper;

import com.filmes.avaliador.dto.request.ListaRecomendacoesCadastroDTO;
import com.filmes.avaliador.model.ListaRecomendacoes;
import com.filmes.avaliador.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public abstract class ListaRecomendacoesMapper {

    @Autowired
    UsersRepository repository;

    @Mapping(target = "usuario", expression = "java( repository.findById( UUID.fromString(dto.idUsuario())).orElse(null) )")
    public abstract ListaRecomendacoes toEntity(ListaRecomendacoesCadastroDTO dto);

    @Mapping(target = "idUsuario", expression = "java( entity.getUsuario().getId().toString() )")
    public abstract ListaRecomendacoesCadastroDTO toDto(ListaRecomendacoes entity);

}
