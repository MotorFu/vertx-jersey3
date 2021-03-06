package com.github.vertx.hk2.integration;

import com.github.vertx.jersey.hk2.HK2JerseyBinder;
import com.github.vertx.jersey.inject.VertxRequestProcessor;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 *
 */
public class IntegrationBinder extends AbstractBinder {
    /**
     * Implement to provide binding definitions using the exposed binding
     * methods.
     */
    @Override
    protected void configure() {

        install(new HK2JerseyBinder());

        bind(ReqProcessor1.class).to(VertxRequestProcessor.class).ranked(10);
        bind(ReqProcessor2.class).to(VertxRequestProcessor.class).ranked(100);

    }
}
