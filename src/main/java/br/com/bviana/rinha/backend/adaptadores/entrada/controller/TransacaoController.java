package br.com.bviana.rinha.backend.adaptadores.entrada.controller;

import br.com.bviana.rinha.backend.adaptadores.entrada.controller.dto.RequisicaoTransacao;
import br.com.bviana.rinha.backend.aplicacao.facade.ExecucaoTransacaoFacade;
import io.javalin.http.Handler;

import java.time.Instant;

public class TransacaoController {
    public static Handler criaTransacao = ctx -> {
        var instanteExato = Instant.now();
        var idCliente = Integer.valueOf(ctx.pathParam("id"));
        var requisicao = ctx.bodyAsClass(RequisicaoTransacao.class);
        var facade = new ExecucaoTransacaoFacade();
        var resposta = facade.executaTransacao(idCliente, requisicao, instanteExato);
        ctx.json(resposta).status(200);
    };

}
