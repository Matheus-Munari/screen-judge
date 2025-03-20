package com.filmes.avaliador.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Avaliador de Filmes",
                version = "1.0",
                description = "API de avaliação e criação de listas de recomendações de filmes",
                contact = @Contact(
                        name = "Matheus Munari Denani",
                        email = "matheusmunari0@gmail.com"
                )
        ),
        security =  {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name =  "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
