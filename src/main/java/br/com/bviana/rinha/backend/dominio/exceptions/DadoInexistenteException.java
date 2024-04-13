package br.com.bviana.rinha.backend.dominio.exceptions;

public class DadoInexistenteException extends RuntimeException {
    public DadoInexistenteException(String message) {
        super(message);
    }
}
