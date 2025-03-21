package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.request.AuthenticationDTO;
import com.filmes.avaliador.dto.request.CodigoConfirmacaoCadastroDTO;
import com.filmes.avaliador.dto.request.UserRequestCadastroDTO;
import com.filmes.avaliador.dto.response.user.CodigoGeradoResponseDTO;
import com.filmes.avaliador.dto.response.user.LoginResponseDTO;
import com.filmes.avaliador.model.user.Users;
import com.filmes.avaliador.security.CustomAuthenticationProvider;
import com.filmes.avaliador.security.TokenService;
import com.filmes.avaliador.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.filmes.avaliador.mapper.UserMapper.toEntity;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final CustomAuthenticationProvider authenticationProvider;

    private final UsersService usersService;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto, Authentication authentication){


        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = this.authenticationProvider.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<CodigoGeradoResponseDTO> registrar(@RequestBody @Valid UserRequestCadastroDTO dto){

        Integer indiceUsuario = usersService.gerarCodigoAutenticacao(toEntity(dto));


        CodigoGeradoResponseDTO codigoGeradoResponseDTO = CodigoGeradoResponseDTO.builder()
                .mensagem("Código enviado para o email informado")
                .indice(indiceUsuario)
                .build();

        return ResponseEntity.accepted().body(codigoGeradoResponseDTO);
    }

    @PostMapping("/register/confirmation")
    public ResponseEntity<Void> cadastrar(@RequestBody CodigoConfirmacaoCadastroDTO dto){
        Users usuario = usersService.cadastrarUsuario(dto.index(), dto.codigo());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/auth/login/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
