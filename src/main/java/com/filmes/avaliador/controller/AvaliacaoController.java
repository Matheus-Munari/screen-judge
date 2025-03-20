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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Tag(name = "Avaliação", description = "Endpoint utilizado para avaliar e comentar avaliações de filmes")
@Slf4j
public class AvaliacaoController {

    private final AvaliacaoService service;

    private final ComentarioAvaliacaoService comentarioAvaliacaoService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Cadastrar nova avaliação", description = "Cadastra uma nova avaliação de um filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "401", description = "Sem autorização"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "422", description = "Erro na validação"),
            @ApiResponse(responseCode = "409", description = "Usuário já avaliou este filme"),
    })
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
    @Operation(summary = "Buscar todas as avaliações", description = "Busca todas as avaliações de filmes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avaliação cadastrada com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhuma avaliação encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "401", description = "Sem autorização"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
    })
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
    @Operation(summary = "Deletar avaliação", description = "Deleta uma avaliação de um filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação deletada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "401", description = "Sem autorização"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada"),
    })
    public ResponseEntity<Void> deletar(@PathVariable Integer id){

        service.deletar(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Atualizar avaliação", description = "Atualiza uma avaliação de um filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "401", description = "Sem autorização"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada"),
    })
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
    @Operation(summary = "Comentar avaliação", description = "Comenta uma avaliação de um filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "401", description = "Sem autorização"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "422", description = "Erro na validação"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada"),
    })
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
    @Operation(summary = "Buscar comentários de uma avaliação", description = "Busca os comentários de uma avaliação de um filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentários encontrados"),
            @ApiResponse(responseCode = "204", description = "Nenhum comentário encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "401", description = "Sem autorização"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada"),
    })
    public ResponseEntity<Page<ComentariosAvaliacaoDTO>> buscarAvaliacaoComComentarios(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "0") Integer pagina,
            @RequestParam(required = false, defaultValue = "10") Integer tamanhoPagina,
            @RequestParam(required = false, defaultValue = "false") boolean orderByAsc){

        Page<ComentarioAvaliacao> avaliacao = comentarioAvaliacaoService.buscarComentariosDeAvaliacao(id, orderByAsc, pagina, tamanhoPagina);

        if(avaliacao.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        var comentariosDto = avaliacao.map(AvaliacaoMapper::toComentariosAvaliacaoDto);
        return ResponseEntity.ok(comentariosDto);
    }

    @DeleteMapping("comentarios/{idComentario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Deletar comentário", description = "Deleta um comentário de uma avaliação de um filme")
    public ResponseEntity<String> deletarComentario(@PathVariable Integer idComentario, Authentication authentication){

        comentarioAvaliacaoService.deletarComentario(idComentario, authentication);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("comentarios/{idComentario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Operation(summary = "Atualizar comentário", description = "Atualiza um comentário de uma avaliação de um filme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição"),
            @ApiResponse(responseCode = "401", description = "Sem autorização"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "422", description = "Erro na validação"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado"),
    })
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
