package br.com.bviana.rinha.backend.dominio.exceptions;

public class DadoInvalidoException extends RuntimeException {

    public DadoInvalidoException(String message) {
        super(message);
    }
}
