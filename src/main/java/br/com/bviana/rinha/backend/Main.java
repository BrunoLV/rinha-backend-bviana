package br.com.bviana.rinha.backend;

import br.com.bviana.rinha.backend.adaptadores.entrada.controller.ExtratoController;
import br.com.bviana.rinha.backend.adaptadores.entrada.controller.TransacaoController;
import br.com.bviana.rinha.backend.dominio.exceptions.DadoInexistenteException;
import br.com.bviana.rinha.backend.dominio.exceptions.DadoInvalidoException;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static void main(String[] args) {

        final String format = "yyyy-MM-dd'T'HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        var app = Javalin.create(config -> {
            config.jetty.modifyServer(s -> {
                s.setAttribute("org.eclipse.jetty.server.ServerConnector.acceptQueueSize", 30000);
                s.setAttribute("org.eclipse.jetty.server.ServerConnector.soLingerTime", -1);
            });
            config.jsonMapper(new JavalinJackson().updateMapper(m -> {
                m.registerModule(new JavaTimeModule());
                m.registerModule(new BlackbirdModule());
                m.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
                m.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                m.setDateFormat(sdf);
            }));
            config.router.apiBuilder(() -> path("/clientes", () -> path("/{id}", () -> {
                path("/transacoes", () -> post(TransacaoController.criaTransacao));
                path("/extrato", () -> get(ExtratoController.getExtrato));
            })));
        });

        app.exception(DadoInexistenteException.class, (e, ctx) -> ctx.status(404));
        app.exception(DadoInvalidoException.class, (e, ctx) -> ctx.status(422));
        app.exception(Exception.class, (e, ctx) -> ctx.status(500));

        app.start(8080);
    }

}