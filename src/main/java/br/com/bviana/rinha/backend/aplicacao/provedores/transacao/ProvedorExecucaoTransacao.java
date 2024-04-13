package br.com.bviana.rinha.backend.aplicacao.provedores.transacao;

import br.com.bviana.rinha.backend.adaptadores.saida.banco_dados.RepositorioContaBDRelacional;
import br.com.bviana.rinha.backend.adaptadores.saida.banco_dados.RepositorioTransacaoBDRelacional;
import br.com.bviana.rinha.backend.dominio.casos_uso.ExecucaoTransacao;

import java.sql.Connection;

public class ProvedorExecucaoTransacao {

    public static ExecucaoTransacao provide(Connection connection) {
        var repositorioTransacao = new RepositorioTransacaoBDRelacional(connection);
        var repositorioConta = new RepositorioContaBDRelacional(connection);
        return new ExecucaoTransacao(repositorioTransacao, repositorioConta);
    }

}
