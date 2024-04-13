package br.com.bviana.rinha.backend.adaptadores.saida.banco_dados;

import br.com.bviana.rinha.backend.aplicacao.exceptions.DataBaseException;
import br.com.bviana.rinha.backend.dominio.conta.modelo.Conta;
import br.com.bviana.rinha.backend.dominio.transacao.modelo.Transacao;
import br.com.bviana.rinha.backend.dominio.transacao.repositorio.RepositorioTransacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTransacaoBDRelacional implements RepositorioTransacao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositorioTransacaoBDRelacional.class);

    private final Connection connection;

    public RepositorioTransacaoBDRelacional(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void salvaTransacao(Conta conta, Transacao transacao) {
        var sqlUpdate = "UPDATE TB_CONTA SET SALDO = ? WHERE ID = ?";
        var sqlInsert = "INSERT INTO TB_TRANSACAO ( VALOR, TIPO, DESCRICAO, DATA_EXECUCAO, ID_CLIENTE) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmtInsert = null;
        PreparedStatement stmtUpdate = null;

        try {
            stmtInsert = this.connection.prepareStatement(sqlInsert);
            stmtInsert.setDouble(1, transacao.getValor());
            stmtInsert.setString(2, transacao.getTipo());
            stmtInsert.setString(3, transacao.getDescricao());
            stmtInsert.setTimestamp(4, Timestamp.valueOf(transacao.getDataExecucao()));
            stmtInsert.setInt(5, transacao.getIdCliente());
            stmtInsert.executeUpdate();

            stmtUpdate = this.connection.prepareStatement(sqlUpdate);
            stmtUpdate.setDouble(1, conta.getSaldo());
            stmtUpdate.setInt(2, conta.getId());
            stmtUpdate.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Erro ocorreu enquanto salvando a transacao. Erro: {}", e.getMessage());
            throw new DataBaseException(e.getMessage(), e);
        } finally {
            try {
                stmtInsert.close();
                stmtUpdate.close();
            } catch (SQLException e) {
                LOGGER.warn("Erro ao fechar os recursos. Erro: {}", e.getMessage());
            }
        }
    }

    @Override
    public List<Transacao> buscaTodasPorIdClient(Integer idCliente) {
        var sql = "SELECT * FROM TB_TRANSACAO WHERE ID_CLIENTE = ? ORDER BY DATA_EXECUCAO DESC LIMIT 10";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idCliente);
            try (var resultSet = preparedStatement.executeQuery()) {
                List<Transacao> transacoes = new ArrayList<>();
                while (resultSet.next()) {
                    var transacao = new Transacao(resultSet.getDouble("VALOR"), resultSet.getString("TIPO"), resultSet.getString("DESCRICAO"), resultSet.getInt("ID_CLIENTE"), resultSet.getTimestamp("DATA_EXECUCAO").toLocalDateTime());
                    transacoes.add(transacao);
                }
                return transacoes;
            }
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage(), e);
        }
    }
}
