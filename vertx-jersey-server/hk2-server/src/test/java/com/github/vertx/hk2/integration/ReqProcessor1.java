package com.github.vertx.hk2.integration;

import com.github.vertx.jersey.inject.VertxRequestProcessor;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import org.glassfish.jersey.server.ContainerRequest;


/**
 *
 */
public class ReqProcessor1 implements VertxRequestProcessor {

    private final Vertx vertx;

    @Inject
    public ReqProcessor1(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * Provide additional async request processing
     *
     * @param vertxRequest  the vert.x http server request
     * @param jerseyRequest the jersey container request
     * @param done          the done async callback handler
     */
    @Override
    public void process(HttpServerRequest vertxRequest, ContainerRequest jerseyRequest, final Handler<Void> done) {
        vertx.runOnContext(done::handle);
    }
}
