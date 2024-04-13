package br.com.bviana.rinha.backend.dominio.conta.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Conta implements Serializable {

    private final Integer id;
    private final Double limite;
    private final Double saldo;

    public Conta(Integer id, Double limite, Double saldo) {
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    public Integer getId() {
        return id;
    }

    public Double getLimite() {
        return limite;
    }

    public Double getSaldo() {
        return saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Conta conta = (Conta) o;
        return Objects.equals(id, conta.id) && Objects.equals(limite, conta.limite) && Objects.equals(saldo, conta.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, limite, saldo);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", limite=" + limite +
                ", saldo=" + saldo +
                '}';
    }

}
