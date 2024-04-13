package br.com.bviana.rinha.backend.aplicacao.facade;

import br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto.RequisicaoTransacao;
import br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto.RespostaTransacao;
import br.com.bviana.rinha.backend.aplicacao.exceptions.DataBaseException;
import br.com.bviana.rinha.backend.aplicacao.provedores.banco_dados.ProvedorDataSource;
import br.com.bviana.rinha.backend.aplicacao.provedores.transacao.ProvedorExecucaoTransacao;
import br.com.bviana.rinha.backend.dominio.transacao.modelo.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class ExecucaoTransacaoFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecucaoTransacaoFacade.class);

    public RespostaTransacao executaTransacao(final Integer id, final RequisicaoTransacao requisicao, final Instant instanteExato) {
        var transacao = new Transacao(requisicao.valor(), requisicao.tipo(), requisicao.descricao(), id, LocalDateTime.ofInstant(instanteExato, ZoneId.systemDefault()));
        transacao.valida();
        var resultado = executa(transacao);
        return new RespostaTransacao(resultado.get("limite").intValue(), resultado.get("saldo").intValue());
    }

    private Map<String, Double> executa(final Transacao transacao) {
        Connection connection = null;
        try {
            connection = ProvedorDataSource.getConnection();
            var resultado = ProvedorExecucaoTransacao.provide(connection).executaTransacao(transacao);
            connection.commit();
            return resultado;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.warn("Ocorreu um erro ao executar o rollback. Erro: {}", e.getMessage());
            }
            throw new DataBaseException(e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.warn("Ocorreu um erro ao fechar a conexão. Erro: {}", e.getMessage());
                }
            }
        }
    }

}
