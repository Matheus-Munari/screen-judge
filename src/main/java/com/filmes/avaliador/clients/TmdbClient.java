package com.filmes.avaliador.clients;

import com.filmes.avaliador.dto.response.tmdb.TmdbResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.crew.TmdbCrewResponseDTO;
import com.filmes.avaliador.dto.response.tmdb.trailler.TmdbTraillerResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${feign.client.config.tmdb.url}", name = "tmdb")
public interface TmdbClient {

    @GetMapping("/search/movie")
    TmdbResponseDTO buscarFilmes(@RequestParam("query") String query,
                                 @RequestParam("language") String language,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "include_adult", required = false) Boolean includeAdult,
                                 @RequestParam("api_key") String apiKey);

    @GetMapping("movie/{id}/credits")
    TmdbCrewResponseDTO buscarCreditos(@PathVariable("id") Long id,
                                       @RequestParam("language") String language,
                                       @RequestParam("api_key") String apiKey
                                       );

    @GetMapping("movie/{id}/videos")
    TmdbTraillerResponseDTO buscarTraillers(@PathVariable("id") Long id,
                                            @RequestParam("language") String language,
                                            @RequestParam("api_key") String apiKey);
}
