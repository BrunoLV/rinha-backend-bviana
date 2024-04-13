package br.com.bviana.rinha.backend.dominio.extrato.modelo;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transacao implements Serializable, Comparable<Transacao> {

    private final Double valor;
    private final String tipo;
    private final String descricao;
    private final LocalDateTime realizadoEm;

    public Transacao(Double valor, String tipo, String descricao, LocalDateTime realizadoEm) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.realizadoEm = realizadoEm;
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

    public LocalDateTime getRealizadoEm() {
        return realizadoEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transacao that = (Transacao) o;
        return Objects.equals(valor, that.valor) && Objects.equals(tipo, that.tipo) && Objects.equals(descricao, that.descricao) && Objects.equals(realizadoEm, that.realizadoEm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor, tipo, descricao, realizadoEm);
    }

    @Override
    public String toString() {
        return "TransacaoExtrato{" +
                "valor=" + valor +
                ", tipo='" + tipo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", realizadoEm=" + realizadoEm +
                '}';
    }

    @Override
    public int compareTo(@NotNull Transacao o) {
        return o.realizadoEm.compareTo(this.realizadoEm);
    }
}
