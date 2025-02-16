package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.FilmeDTO;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.service.FilmeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.filmes.avaliador.mapper.FilmeMapper.*;

@RequestMapping("/filmes")
@RestController
public class FilmeController {

    private FilmeService service;

    public FilmeController(FilmeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody FilmeDTO dto){

        Filme filmeSalvo = service.cadastrarFilme(toEntity(dto));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(filmeSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Filme>> buscarTodos(){
        List<Filme> filmes = service.buscarFilmes();

        return ResponseEntity.ok(filmes);
    }

}
