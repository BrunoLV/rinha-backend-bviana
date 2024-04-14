package br.com.bviana.rinha.backend.aplicacao.provedores.banco_dados;

import br.com.bviana.rinha.backend.aplicacao.exceptions.DataBaseException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ProvedorDataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASS"));

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(10);

        config.setConnectionTestQuery("SELECT 1");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("validationTimeout", "5000");
        config.setConnectionInitSql("SELECT 1");
        config.setAutoCommit(false);
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new DataBaseException("Erro ao tentar conectar no banco de dados.", e);
        }
    }

}
