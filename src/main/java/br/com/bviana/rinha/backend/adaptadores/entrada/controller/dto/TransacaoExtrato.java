package br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto;

import java.time.LocalDateTime;

public record TransacaoExtrato(Integer valor, String tipo, String descricao, LocalDateTime realizadoEm) {
}
