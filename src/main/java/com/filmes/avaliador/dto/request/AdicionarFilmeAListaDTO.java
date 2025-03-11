package com.filmes.avaliador.dto.request;

import lombok.Builder;
import org.apache.kafka.common.serialization.StringDeserializer;

@Builder
public record AdicionarFilmeAListaDTO(Long listaRecomendacoesId, Long filmeId) {
}
