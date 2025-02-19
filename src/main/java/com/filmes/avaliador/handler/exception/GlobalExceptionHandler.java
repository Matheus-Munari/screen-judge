package com.filmes.avaliador.handler.exception;

import com.filmes.avaliador.dto.response.exception.ErroCampo;
import com.filmes.avaliador.dto.response.exception.ErroResponseDTO;
import com.filmes.avaliador.exception.ConflitoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(ConflitoException.class)
    public ErroResponseDTO handlerConflitoException(ConflitoException exception){
        return new ErroResponseDTO(
                409,
                exception.getMessage(),
                List.of()
        );
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResponseDTO handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors.stream().map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaErros
        );
    }

}
