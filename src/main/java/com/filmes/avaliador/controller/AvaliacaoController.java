package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.request.AvaliacaoDTO;
import static com.filmes.avaliador.mapper.AvaliacaoMapper.*;

import com.filmes.avaliador.dto.response.avaliacao.AvaliacaoUsuarioFilmeResponseDTO;
import com.filmes.avaliador.mapper.AvaliacaoMapper;
import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.service.AvaliacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private AvaliacaoService service;

    public AvaliacaoController(AvaliacaoService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> cadastrar(@RequestBody AvaliacaoDTO avaliacaoDTO){


        Avaliacao avaliacao = service.cadastrarNovaAvaliacao(toEntity(avaliacaoDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(avaliacao.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<AvaliacaoUsuarioFilmeResponseDTO>> todasAvaliacoes(){

        List<Avaliacao> avaliacoes = service.buscarTodas();

        if(avaliacoes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<AvaliacaoUsuarioFilmeResponseDTO> avaliacoesDto = avaliacoes.stream().map(AvaliacaoMapper::toAvaliacaoUsuarioFilmeDto).toList();

        return ResponseEntity.ok(avaliacoesDto);

    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){

        service.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer id,
            @RequestBody AvaliacaoDTO avaliacao){

        Avaliacao avaliacaoEntidade = toEntity(avaliacao);
        avaliacaoEntidade.setId(id);

        service.atualizar(avaliacaoEntidade);
        return ResponseEntity.noContent().build();
    }


}
