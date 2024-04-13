package br.com.bviana.rinha.backend.aplicacao.provedores.extrato;

import br.com.bviana.rinha.backend.adaptadores.saida.banco_dados.RepositorioContaBDRelacional;
import br.com.bviana.rinha.backend.adaptadores.saida.banco_dados.RepositorioTransacaoBDRelacional;
import br.com.bviana.rinha.backend.dominio.casos_uso.ExtracaoExtrato;

import java.sql.Connection;

public class ProvedorExtracaoExtrato {

    public static ExtracaoExtrato provide(Connection connection) {
        var repositorioTransacao = new RepositorioTransacaoBDRelacional(connection);
        var repositorioConta = new RepositorioContaBDRelacional(connection);
        return new ExtracaoExtrato(repositorioTransacao, repositorioConta);
    }

}
