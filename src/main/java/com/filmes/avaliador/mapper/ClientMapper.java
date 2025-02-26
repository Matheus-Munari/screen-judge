package com.filmes.avaliador.mapper;


import com.filmes.avaliador.dto.request.ClientSaveDTO;
import com.filmes.avaliador.model.Client;

public class ClientMapper {

    public static Client toEntity(ClientSaveDTO dto){
        return Client.builder()
                .clientId(dto.clientId())
                .clientSecret(dto.clientSecret())
                .redirectURI(dto.redirectURI())
                .scope(dto.scope())
                .build();
    }


}
