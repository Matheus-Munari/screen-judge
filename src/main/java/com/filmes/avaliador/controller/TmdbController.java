package com.filmes.avaliador.controller;

import com.filmes.avaliador.clients.TmdbClient;
import com.filmes.avaliador.config.TmdbClientConfig;
import com.filmes.avaliador.dto.response.filme.FilmePorIdResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.TmdbResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.filme.FilmePorIdTmdbResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.trailler.TraillerResponseDTO;
import com.filmes.avaliador.service.FilmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tmdb/filmes")
@RequiredArgsConstructor
public class TmdbController {

    private final TmdbClient tmdbClient;

    private final TmdbClientConfig tmdbClientConfig;

    private final FilmeService filmeService;

    @GetMapping
    public ResponseEntity<TmdbResponseDTO> buscarFilmesTmdb(@RequestParam String filme,
                                                            @RequestParam(required = false, value = "1") Integer pagina,
                                                            @RequestParam(required = false, value = "false") Boolean incluirAdulto){

        TmdbResponseDTO dto = filmeService.buscarFilmesTmdb(filme, incluirAdulto, pagina);

        if(dto.filmes().isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/crew/{id}")
    public ResponseEntity<String> buscarCrew(@PathVariable("id") Long id){

//        TmdbCrewResponseDTO dto =tmdbClient.buscarCreditos(id, "pt-BR", tmdbClientConfig.getKey());

        String diretor = filmeService.buscarDiretor(id);
        return ResponseEntity.ok(diretor);
    }

    @GetMapping("/trailer/{id}")
    public ResponseEntity<TraillerResponseDTO> buscarTrailler(@PathVariable("id") Long id){

        TraillerResponseDTO dto = filmeService.buscarTrailer(id);

        return ResponseEntity.ok(dto);

    }

    @GetMapping("{id}")
    public ResponseEntity<FilmePorIdResponseDTO> buscarPorId(@PathVariable("id") Long id){

        FilmePorIdResponseDTO dto = filmeService.buscarTmdbPorId(id);

        return ResponseEntity.ok(dto);

    }

}
