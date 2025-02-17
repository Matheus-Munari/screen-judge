package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.request.UserRequestCadastroDTO;
import static com.filmes.avaliador.mapper.UserMapper.*;

import com.filmes.avaliador.dto.response.user.UserResponseDTO;
import com.filmes.avaliador.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private UsersService service;

    public UserController(UsersService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid UserRequestCadastroDTO dto){

        service.cadastrarUsuario(toEntity(dto));

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(
            @RequestBody @Valid UserRequestCadastroDTO dto,
            @PathVariable String id){

        service.atualizarUsuario(toEntity(dto), UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId(@PathVariable String id){

        UserResponseDTO userDto = toResponseDTO(service.usuarioPorId(UUID.fromString(id)));
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> buscarTodos(@RequestParam(required = false) String nome){

        List<UserResponseDTO> users = service.listarUsuarios(nome)
                .stream()
                .map(usuario -> new UserResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getDataNascimento(),
                        usuario.getAtivo())).toList();

        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id){
        service.deletarUsuario(UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }
}
