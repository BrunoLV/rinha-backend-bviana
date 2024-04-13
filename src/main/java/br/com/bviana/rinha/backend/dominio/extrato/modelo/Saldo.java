package br.com.bviana.rinha.backend.dominio.extrato.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Saldo implements Serializable {

    private final Integer total;
    private final LocalDateTime dataExtrato;
    private final Integer limite;

    public Saldo(Integer total, LocalDateTime dataExtrato, Integer limite) {
        this.total = total;
        this.dataExtrato = dataExtrato;
        this.limite = limite;
    }

    public Integer getTotal() {
        return total;
    }

    public LocalDateTime getDataExtrato() {
        return dataExtrato;
    }

    public Integer getLimite() {
        return limite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Saldo saldo = (Saldo) o;
        return Objects.equals(total, saldo.total) && Objects.equals(dataExtrato, saldo.dataExtrato) && Objects.equals(limite, saldo.limite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, dataExtrato, limite);
    }

    @Override
    public String toString() {
        return "Saldo{" +
                "total=" + total +
                ", dataExtrato=" + dataExtrato +
                ", limite=" + limite +
                '}';
    }
}
