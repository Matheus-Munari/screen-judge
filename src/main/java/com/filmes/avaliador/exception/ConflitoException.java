package com.filmes.avaliador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflitoException extends RuntimeException{


    public ConflitoException(String message) {
        super(message);
    }

    public ConflitoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflitoException(Throwable cause) {
        super(cause);
    }

    protected ConflitoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
