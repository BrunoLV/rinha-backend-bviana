package br.com.bviana.rinha.backend.dominio.extrato.modelo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Extrato implements Serializable {

    private final Saldo saldo;
    private final List<Transacao> ultimasTransacoes;

    public Extrato(Saldo saldo, List<Transacao> ultimasTransacoes) {
        this.saldo = saldo;
        this.ultimasTransacoes = ultimasTransacoes;
    }

    public Saldo getSaldo() {
        return saldo;
    }

    public List<Transacao> getUltimasTransacoes() {
        return ultimasTransacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Extrato extrato = (Extrato) o;
        return Objects.equals(saldo, extrato.saldo) && Objects.equals(ultimasTransacoes, extrato.ultimasTransacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saldo, ultimasTransacoes);
    }

    @Override
    public String toString() {
        return "Extrato{" +
                "saldo=" + saldo +
                ", ultimasTransacoes=" + ultimasTransacoes +
                '}';
    }
}
