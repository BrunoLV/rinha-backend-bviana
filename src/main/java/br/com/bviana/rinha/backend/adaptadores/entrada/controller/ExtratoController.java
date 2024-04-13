package br.com.bviana.rinha.backend.adaptadores.entrada.controller;

import br.com.bviana.rinha.backend.aplicacao.facade.ExtracaoExtratoFacade;
import io.javalin.http.Handler;

public class ExtratoController {

    public static Handler getExtrato = ctx -> {
        var idCliente = Integer.valueOf(ctx.pathParam("id"));
        var facade = new ExtracaoExtratoFacade();
        var extrato = facade.extraiExtrato(idCliente);
        ctx.json(extrato).status(200);
    };

}
