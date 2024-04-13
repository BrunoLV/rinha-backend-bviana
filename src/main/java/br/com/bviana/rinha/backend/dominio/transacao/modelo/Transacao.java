package br.com.bviana.rinha.backend.dominio.transacao.modelo;

import br.com.bviana.rinha.backend.dominio.exceptions.DadoInvalidoException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class Transacao implements Serializable {

    private static final Set<String> TIPOS_PERMITIDOS = Set.of("c", "d");

    private final Double valor;
    private final String tipo;
    private final String descricao;
    private final Integer idCliente;
    private final LocalDateTime dataExecucao;

    public Transacao(Double valor, String tipo, String descricao, Integer idCliente, LocalDateTime dataExecucao) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.idCliente = idCliente;
        this.dataExecucao = dataExecucao;
    }

    public Double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transacao transacao = (Transacao) o;
        return Objects.equals(valor, transacao.valor) && Objects.equals(tipo, transacao.tipo) && Objects.equals(descricao, transacao.descricao) && Objects.equals(idCliente, transacao.idCliente) && Objects.equals(dataExecucao, transacao.dataExecucao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, tipo, descricao, idCliente, dataExecucao);
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "valor=" + valor +
                ", tipo='" + tipo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idCliente=" + idCliente +
                ", dataExecucao=" + dataExecucao +
                '}';
    }

    public void valida() {
        if (this.valor == null || this.valor.equals(0.0)) {
            throw new DadoInvalidoException("Valor não informado.");
        }
        if (!this.valor.equals(Math.floor(this.valor))) {
            throw new DadoInvalidoException("Valor não é um inteiro");
        }
        if (this.tipo == null || this.tipo.trim().isEmpty() || !TIPOS_PERMITIDOS.contains(this.tipo.toLowerCase(Locale.ROOT))) {
            throw new DadoInvalidoException("Tipo não é um valor valido.");
        }
        if (this.descricao == null || this.descricao.trim().isEmpty() || this.descricao.length() > 10) {
            throw new DadoInvalidoException("Descrição é um valor invalido.");
        }
    }
}
