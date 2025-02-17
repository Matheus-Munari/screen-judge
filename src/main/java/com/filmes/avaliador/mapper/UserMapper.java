package com.filmes.avaliador.mapper;

import com.filmes.avaliador.dto.request.UserRequestCadastroDTO;
import com.filmes.avaliador.dto.response.user.UserResponseDTO;
import com.filmes.avaliador.model.Users;

public class UserMapper {

    public static Users toEntity(UserRequestCadastroDTO dto){

        return Users.builder()
                .nome(dto.nome())
                .senha(dto.senha())
                .email(dto.email())
                .dataNascimento(dto.dataNascimento())
                .build();
    }

    public static UserResponseDTO toResponseDTO(Users user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .nome(user.getNome())
                .email(user.getEmail())
                .dataNascimento(user.getDataNascimento())
                .ativo(user.getAtivo())
                .build();
    }
}
