package br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto;

import java.time.LocalDateTime;

public record SaldoExtrato(Integer total, LocalDateTime dataExtrato, Integer limite) {
}
