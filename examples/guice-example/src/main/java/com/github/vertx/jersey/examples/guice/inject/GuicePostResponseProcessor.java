package com.github.vertx.jersey.examples.guice.inject;

import com.github.vertx.jersey.inject.VertxPostResponseProcessor;
import io.vertx.core.http.HttpServerResponse;
import org.glassfish.jersey.server.ContainerResponse;

/**
 * Example guice implementation of {@link com.github.vertx.jersey.inject.VertxPostResponseProcessor}
 */
public class GuicePostResponseProcessor implements VertxPostResponseProcessor {
    /**
     * <p>
     * Provide processing after the the vert.x response end() method has been called
     * </p>
     *
     * @param vertxResponse  the vert.x http server response
     * @param jerseyResponse the jersey container response
     */
    @Override
    public void process(HttpServerResponse vertxResponse, ContainerResponse jerseyResponse) {
        if (jerseyResponse != null) {

        }
    }
}
