package com.filmes.avaliador.controller;


import com.filmes.avaliador.dto.request.AdicionarFilmeAListaDTO;
import com.filmes.avaliador.dto.request.ListaRecomendacoesCadastroDTO;
import com.filmes.avaliador.dto.response.filme.FilmeResponseDTO;
import com.filmes.avaliador.mapper.FilmeMapper;
import com.filmes.avaliador.mapper.ListaRecomendacoesMapper;
import com.filmes.avaliador.model.Filme;
import com.filmes.avaliador.model.ListaRecomendacoes;
import com.filmes.avaliador.service.ListaRecomendacoesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/lista-recomendacoes")
@RequiredArgsConstructor
public class ListaRecomendacoesController {

    private final ListaRecomendacoesService service;

    private final ListaRecomendacoesMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid ListaRecomendacoesCadastroDTO dto){

        ListaRecomendacoes listaRecomendacoes = service.cadastrar(mapper.toEntity(dto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(listaRecomendacoes.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<ListaRecomendacoesCadastroDTO>> buscarPorId(
                                                                @RequestParam(required = false) String nome,
                                                                @RequestParam(required = false) String genero,
                                                                @RequestParam(required = false) String nomeUsuario,
                                                                @RequestParam(defaultValue = "10") Integer tamanhoPagina,
                                                                @RequestParam(defaultValue = "0") Integer pagina){
        Page<ListaRecomendacoes> listaRecomendacoesPage = service.buscar(nomeUsuario, genero, nome, tamanhoPagina, pagina);

        Page<ListaRecomendacoesCadastroDTO> resultado = listaRecomendacoesPage.map(mapper::toDto);

        if(resultado.getContent().isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(resultado);

    }

    @PostMapping("add-filme")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> adicionarFilme(@RequestBody AdicionarFilmeAListaDTO dto){
        service.adicionarFilme(dto.listaRecomendacoesId(), dto.filmeId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<FilmeResponseDTO>> buscarFilmesDaLista(@PathVariable Long id,
                                                                      @RequestParam(defaultValue = "10") Integer tamanhoPagina,
                                                                      @RequestParam(defaultValue = "0") Integer pagina){
        Page<Filme> filmes = service.buscarFilmesDaLista(id, tamanhoPagina, pagina);

        var filmesDto = filmes.map(FilmeMapper::toFilmeResponseDTO);
        return ResponseEntity.ok(filmesDto);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deletarLista(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idLista}/filme/{idFilme}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deletarFilmeDaLista(@PathVariable Long idLista, @PathVariable Long idFilme){
        service.removerFilme(idLista, idFilme);
        return ResponseEntity.noContent().build();
    }

}
