package br.com.bviana.rinha.backend.aplicacao.exceptions;

public class DataBaseException extends RuntimeException {

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
