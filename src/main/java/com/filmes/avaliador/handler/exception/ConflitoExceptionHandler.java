package com.filmes.avaliador.handler.exception;

import com.filmes.avaliador.dto.response.ErroCampoDTO;
import com.filmes.avaliador.dto.response.ErroResponseDTO;
import com.filmes.avaliador.exception.ConflitoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ConflitoExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(ConflitoException.class)
    public ErroResponseDTO handler(ConflitoException exception){
        return new ErroResponseDTO(
                409,
                exception.getMessage(),
                List.of(
                    new ErroCampoDTO(
                            exception.getCampo(),
                            exception.getErro()
                    )
                )
        );
    }

}
