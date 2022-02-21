package com.github.vertx.jersey.examples.guice.inject;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Example guice implementation of {@link jakarta.ws.rs.container.ContainerResponseFilter}
 */
public class GuiceResponseFilter implements ContainerResponseFilter {
    /**
     * {@inheritDoc}
     */
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (responseContext != null) {

        }
    }
}
