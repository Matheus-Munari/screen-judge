package com.filmes.avaliador.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "feign.client.config.tmdb")
public class TmdbClientConfig {

    private String url;
    private String key;

}
