package br.com.bviana.rinha.backend.dominio.transacao.repositorio;

import br.com.bviana.rinha.backend.dominio.conta.modelo.Conta;
import br.com.bviana.rinha.backend.dominio.transacao.modelo.Transacao;

import java.util.List;

public interface RepositorioTransacao {

    void salvaTransacao(Conta conta, Transacao transacao);

    List<Transacao> buscaTodasPorIdClient(Integer idCliente);

}
