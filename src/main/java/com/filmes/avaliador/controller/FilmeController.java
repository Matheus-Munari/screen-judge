package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.request.FilmeDTO;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import com.filmes.avaliador.mapper.FilmeMapper;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.service.FilmeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Year;
import java.util.List;

import static com.filmes.avaliador.mapper.FilmeMapper.*;

@RequestMapping("/filmes")
@RestController
@RequiredArgsConstructor
public class FilmeController {

    private final FilmeService service;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid FilmeDTO dto){

        Filme filmeSalvo = service.cadastrarFilme(toEntity(dto));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(filmeSalvo.getId())
                .toUri();

        kafkaTemplate.send("teste-topico", toFilmeResponseDTO(filmeSalvo));
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Page<FilmeResponseDTO>> buscarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String diretor,
            @RequestParam(required = false) Year anoLancamento,
            @RequestParam(required = false) String genero,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "10") Integer tamanhoPagina){
        var paginaResultado = service.buscarFilmesSpecs(
                titulo,
                diretor,
                anoLancamento,
                genero,
                pagina,
                tamanhoPagina);

        Page<FilmeResponseDTO> resultado = paginaResultado.map(FilmeMapper::toFilmeResponseDTO);

        if(resultado.getContent().isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);
    }

}
