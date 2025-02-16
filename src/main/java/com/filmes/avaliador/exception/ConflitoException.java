package com.filmes.avaliador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflitoException extends RuntimeException{

    private String campo;

    private String erro;

    public ConflitoException(String message, String campo, String erro) {
        super(message);
        this.campo = campo;
        this.erro = erro;
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

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
