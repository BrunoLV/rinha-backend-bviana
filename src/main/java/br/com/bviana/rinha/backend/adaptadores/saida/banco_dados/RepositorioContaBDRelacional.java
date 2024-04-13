package br.com.bviana.rinha.backend.adaptadores.saida.banco_dados;

import br.com.bviana.rinha.backend.aplicacao.exceptions.DataBaseException;
import br.com.bviana.rinha.backend.dominio.conta.modelo.Conta;
import br.com.bviana.rinha.backend.dominio.conta.repositorio.RepositorioConta;

import java.sql.Connection;
import java.sql.SQLException;

public class RepositorioContaBDRelacional implements RepositorioConta {

    private final Connection connection;

    public RepositorioContaBDRelacional(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Conta buscaContaPorIdCliente(final Integer idCliente) {
        var sql = "SELECT * FROM TB_CONTA WHERE ID = ?";
        return obtemContaAPartirDaConsulta(idCliente, sql);
    }

    @Override
    public Conta buscaContaPorIdClienteLockando(final Integer idCliente) {
        var sql = "SELECT * FROM TB_CONTA WHERE ID = ? FOR UPDATE";
        return obtemContaAPartirDaConsulta(idCliente, sql);
    }

    private Conta obtemContaAPartirDaConsulta(Integer idCliente, String sql) {
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idCliente);
            try (var resultSet = preparedStatement.executeQuery()) {
                Conta conta = null;
                if (resultSet.next()) {
                    conta = new Conta(resultSet.getInt("id"), resultSet.getDouble("limite"), resultSet.getDouble("saldo"));
                }
                return conta;
            }
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage(), e);
        }
    }
}
