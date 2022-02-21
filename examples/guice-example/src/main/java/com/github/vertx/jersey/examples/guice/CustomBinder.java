package com.github.vertx.jersey.examples.guice;

import com.github.vertx.jersey.examples.guice.impl.DefaultMyDependency;
import com.github.vertx.jersey.examples.guice.inject.GuiceExceptionMapper;
import com.github.vertx.jersey.examples.guice.inject.GuicePostResponseProcessor;
import com.github.vertx.jersey.examples.guice.inject.GuiceRequestFilter;
import com.github.vertx.jersey.examples.guice.inject.GuiceRequestProcessor;
import com.github.vertx.jersey.examples.guice.inject.GuiceResponseFilter;
import com.github.vertx.jersey.examples.guice.inject.GuiceResponseProcessor;
import com.github.vertx.jersey.guice.GuiceJerseyBinder;
import com.github.vertx.jersey.inject.VertxPostResponseProcessor;
import com.github.vertx.jersey.inject.VertxRequestProcessor;
import com.github.vertx.jersey.inject.VertxResponseProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 * Custom guice binder
 */
public class CustomBinder extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {

        install(new GuiceJerseyBinder());

        // POJOs
        bind(MyDependency.class).to(DefaultMyDependency.class);

        // vertx-mod-jersey interfaces
        Multibinder.newSetBinder(binder(), VertxRequestProcessor.class).addBinding().to(GuiceRequestProcessor.class);
        Multibinder.newSetBinder(binder(), VertxResponseProcessor.class).addBinding().to(GuiceResponseProcessor.class);
        Multibinder.newSetBinder(binder(), VertxPostResponseProcessor.class).addBinding().to(GuicePostResponseProcessor.class);

        // Jersey interfaces
        Multibinder.newSetBinder(binder(), ContainerRequestFilter.class).addBinding().to(GuiceRequestFilter.class);
        Multibinder.newSetBinder(binder(), ContainerResponseFilter.class).addBinding().to(GuiceResponseFilter.class);

        Multibinder.newSetBinder(binder(), ExceptionMapper.class).addBinding().to(GuiceExceptionMapper.class);

    }

}
