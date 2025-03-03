package com.filmes.avaliador.dto.response.email;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDTO {

    private String tipo;
    private String destinatario;
    private String assunto;
    private String corpo;
    private String codigo;
    private String nome;
}
