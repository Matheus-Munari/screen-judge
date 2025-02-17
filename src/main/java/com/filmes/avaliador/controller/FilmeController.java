package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.request.FilmeDTO;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import com.filmes.avaliador.mapper.FilmeMapper;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.service.FilmeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Year;
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
    public ResponseEntity<Void> salvar(@RequestBody @Valid FilmeDTO dto){

        Filme filmeSalvo = service.cadastrarFilme(toEntity(dto));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(filmeSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<FilmeResponseDTO>> buscarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String diretor,
            @RequestParam(required = false) Year anoLancamento,
            @RequestParam(required = false) String genero){
        List<Filme> filmes = service.buscarFilmes(
                titulo,
                diretor,
                anoLancamento,
                genero);

        if(filmes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<FilmeResponseDTO> filmesDto = filmes.stream().map(FilmeMapper::toFilmeResponseDTO).toList();
        return ResponseEntity.ok(filmesDto);
    }

}
