package com.filmes.avaliador.dto.response.exception;

import java.util.List;

public record ErroResponseDTO(int status, String mensagem, List<ErroCampo> erros) {
}
