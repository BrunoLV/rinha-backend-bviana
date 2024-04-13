package br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto;

import java.util.List;

public record RespostaExtrato(SaldoExtrato saldo, List<TransacaoExtrato> ultimasTransacoes) {
}
