package com.github.vertx.jersey.examples.guice.inject;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

import java.io.IOException;

/**
 * Example guice implementation of {@link jakarta.ws.rs.container.ContainerRequestFilter}
 */
public class GuiceRequestFilter implements ContainerRequestFilter {
    /**
     * {@inheritDoc}
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext != null) {

        }
    }
}
