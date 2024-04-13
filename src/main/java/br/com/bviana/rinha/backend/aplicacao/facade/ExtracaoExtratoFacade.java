package br.com.bviana.rinha.backend.aplicacao.facade;

import br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto.RespostaExtrato;
import br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto.SaldoExtrato;
import br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto.TransacaoExtrato;
import br.com.bviana.rinha.backend.aplicacao.exceptions.DataBaseException;
import br.com.bviana.rinha.backend.aplicacao.provedores.banco_dados.ProvedorDataSource;
import br.com.bviana.rinha.backend.aplicacao.provedores.extrato.ProvedorExtracaoExtrato;

import java.sql.SQLException;

public class ExtracaoExtratoFacade {

    public RespostaExtrato extraiExtrato(Integer idCliente) {
        try (var connection = ProvedorDataSource.getConnection()) {
            var extracaoExtrato = ProvedorExtracaoExtrato.provide(connection);
            var extrato = extracaoExtrato.extraiExtrato(idCliente);
            return new RespostaExtrato(new SaldoExtrato(extrato.getSaldo().getTotal(), extrato.getSaldo().getDataExtrato(), extrato.getSaldo().getLimite()),
                    extrato.getUltimasTransacoes().stream().map(t -> new TransacaoExtrato(t.getValor().intValue(), t.getTipo(), t.getDescricao(), t.getRealizadoEm())).toList());
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage(), e);
        }
    }
}
