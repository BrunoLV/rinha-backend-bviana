package br.com.bviana.rinha.backend.dominio.casos_uso;

import br.com.bviana.rinha.backend.dominio.conta.repositorio.RepositorioConta;
import br.com.bviana.rinha.backend.dominio.exceptions.DadoInexistenteException;
import br.com.bviana.rinha.backend.dominio.extrato.modelo.Extrato;
import br.com.bviana.rinha.backend.dominio.extrato.modelo.Saldo;
import br.com.bviana.rinha.backend.dominio.extrato.modelo.Transacao;
import br.com.bviana.rinha.backend.dominio.transacao.repositorio.RepositorioTransacao;

import java.time.LocalDateTime;

public class ExtracaoExtrato {

    private final RepositorioTransacao repositorioTransacao;
    private final RepositorioConta repositorioConta;

    public ExtracaoExtrato(RepositorioTransacao repositorioTransacao, RepositorioConta repositorioConta) {
        this.repositorioTransacao = repositorioTransacao;
        this.repositorioConta = repositorioConta;
    }

    public Extrato extraiExtrato(Integer idCliente) {
        var conta = repositorioConta.buscaContaPorIdCliente(idCliente);
        if (conta == null) {
            throw new DadoInexistenteException("Conta não existe.");
        }
        var transacoes = repositorioTransacao.buscaTodasPorIdClient(idCliente);
        var saldoExtrato = new Saldo(conta.getSaldo().intValue(), LocalDateTime.now(), conta.getLimite().intValue());
        var transacoesExtrato = transacoes.stream().map(t -> new Transacao(t.getValor(), t.getTipo(), t.getDescricao(), t.getDataExecucao())).toList();
        return new Extrato(saldoExtrato, transacoesExtrato);
    }

}
