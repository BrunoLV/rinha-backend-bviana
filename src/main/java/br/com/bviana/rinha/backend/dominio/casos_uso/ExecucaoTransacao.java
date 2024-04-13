package br.com.bviana.rinha.backend.dominio.casos_uso;

import br.com.bviana.rinha.backend.dominio.conta.modelo.Conta;
import br.com.bviana.rinha.backend.dominio.conta.repositorio.RepositorioConta;
import br.com.bviana.rinha.backend.dominio.exceptions.DadoInexistenteException;
import br.com.bviana.rinha.backend.dominio.exceptions.DadoInvalidoException;
import br.com.bviana.rinha.backend.dominio.transacao.modelo.Transacao;
import br.com.bviana.rinha.backend.dominio.transacao.repositorio.RepositorioTransacao;

import java.util.Map;

public class ExecucaoTransacao {

    private final RepositorioTransacao repositorioTransacao;
    private final RepositorioConta repositorioConta;

    public ExecucaoTransacao(RepositorioTransacao repositorioTransacao, RepositorioConta repositorioConta) {
        this.repositorioTransacao = repositorioTransacao;
        this.repositorioConta = repositorioConta;
    }

    public Map<String, Double> executaTransacao(final Transacao transacao) {

        var conta = repositorioConta.buscaContaPorIdClienteLockando(transacao.getIdCliente());

        if (conta == null) {
            throw new DadoInexistenteException("Conta não existe");
        }

        var saldoAtual = conta.getSaldo();

        switch (transacao.getTipo()) {
            case "c" -> saldoAtual += transacao.getValor();
            case "d" -> {
                if ((saldoAtual + conta.getLimite()) >= transacao.getValor()) {
                    saldoAtual -= transacao.getValor();
                } else {
                    throw new DadoInvalidoException("Saldo insuficiente.");
                }
            }
        }

        var contaAtualizada = new Conta(conta.getId(), conta.getLimite(), saldoAtual);
        repositorioTransacao.salvaTransacao(contaAtualizada, transacao);
        return Map.of("limite", conta.getLimite(), "saldo", saldoAtual);
    }

}
