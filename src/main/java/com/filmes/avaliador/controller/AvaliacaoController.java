package com.filmes.avaliador.controller;

import com.filmes.avaliador.dto.request.AvaliacaoDTO;
import static com.filmes.avaliador.mapper.AvaliacaoMapper.*;

import com.filmes.avaliador.dto.request.ComentarioRequestDTO;
import com.filmes.avaliador.dto.response.avaliacao.AvaliacaoUsuarioFilmeResponseDTO;
import com.filmes.avaliador.dto.response.avaliacao.ComentariosAvaliacaoDTO;
import com.filmes.avaliador.mapper.AvaliacaoMapper;
import com.filmes.avaliador.model.Avaliacao;
import com.filmes.avaliador.model.Comentario;
import com.filmes.avaliador.model.ComentarioAvaliacao;
import com.filmes.avaliador.service.AvaliacaoService;
import com.filmes.avaliador.service.ComentarioAvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService service;

    private final ComentarioAvaliacaoService comentarioAvaliacaoService;

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

    @PostMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> comentarAvaliacao(
            @PathVariable Integer id,
            @RequestBody ComentarioRequestDTO comentario){

        ComentarioAvaliacao comentarioAvaliacao = comentarioAvaliacaoService.cadastrarNovoComentario(id, comentario.comentario(), UUID.fromString(comentario.idUsuario()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comentarioAvaliacao.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<ComentariosAvaliacaoDTO>> buscarAvaliacaoComComentarios(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "0") Integer pagina,
            @RequestParam(required = false, defaultValue = "10") Integer tamanhoPagina,
            @RequestParam(required = false, defaultValue = "false") boolean orderByAsc){

        Page<ComentarioAvaliacao> avaliacao = comentarioAvaliacaoService.buscarComentariosDeAvaliacao(id, orderByAsc, pagina, tamanhoPagina);

        if(avaliacao.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        var emailDto = avaliacao.map(AvaliacaoMapper::toComentarioAvaliacaoEmailDto);

        var comentariosDto = avaliacao.map(AvaliacaoMapper::toComentariosAvaliacaoDto);
        return ResponseEntity.ok(comentariosDto);
    }

    @DeleteMapping("comentarios/{idComentario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> deletarAvaliacao(@PathVariable Integer idComentario, Authentication authentication){

        comentarioAvaliacaoService.deletarComentario(idComentario, authentication);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("comentarios/{idComentario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> atualizarComentario(
            @PathVariable Integer idComentario,
            @RequestBody ComentarioRequestDTO comentario,
            Authentication authentication){

        ComentarioAvaliacao comentarioAvaliacao = comentarioAvaliacaoService.atualizarComentario(
                idComentario,
                comentario.comentario(),
                comentario.idUsuario(),
                authentication);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(comentarioAvaliacao.getComentario().getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


}
