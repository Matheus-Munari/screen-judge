package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.request.AuthenticationDTO;
import com.filmes.avaliador.dto.request.UserRequestCadastroDTO;
import com.filmes.avaliador.dto.response.user.LoginResponseDTO;
import com.filmes.avaliador.model.user.Users;
import com.filmes.avaliador.security.CustomAuthenticationProvider;
import com.filmes.avaliador.security.TokenService;
import com.filmes.avaliador.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class AuthenticationController {

    private final CustomAuthenticationProvider authenticationProvider;

    private final UsersService usersService;

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = this.authenticationProvider.authenticate(usernamePassword);

        var token = tokenService.gerarToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registrar(@RequestBody @Valid UserRequestCadastroDTO dto){
        Users usuario =  usersService.cadastrarUsuario(toEntity(dto));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/auth/login/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
