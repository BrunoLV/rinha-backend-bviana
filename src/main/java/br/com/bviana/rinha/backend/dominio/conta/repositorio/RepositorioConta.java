package br.com.bviana.rinha.backend.dominio.conta.repositorio;

import br.com.bviana.rinha.backend.dominio.conta.modelo.Conta;

public interface RepositorioConta {

    Conta buscaContaPorIdCliente(Integer idCliente);

    Conta buscaContaPorIdClienteLockando(Integer idCliente);
}
