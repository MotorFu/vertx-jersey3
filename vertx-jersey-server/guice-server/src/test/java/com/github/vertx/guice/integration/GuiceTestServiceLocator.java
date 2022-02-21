package com.github.vertx.guice.integration;

import com.github.vertx.jersey.JerseyVerticle;
import com.github.vertx.jersey.guice.WhenGuiceJerseyBinder;
import com.github.vertx.jersey.guice.loader.GuiceVertxBinder;
import com.github.vertx.jersey.integration.TestServiceLocator;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.util.Arrays;
import java.util.List;

/**
 * Guice implementation of {@link TestServiceLocator}
 */
public class GuiceTestServiceLocator implements TestServiceLocator {

    protected Injector injector;

    @Override
    public void init(Vertx vertx) {
        injector = Guice.createInjector(getModules(vertx));
    }

    protected List<Module> getModules(Vertx vertx) {
        return Arrays.asList(
                new GuiceVertxBinder(vertx),
                new WhenGuiceJerseyBinder());
    }

    @Override
    public void tearDown() {
        injector = null;
    }

    @Override
    public <T> T getService(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    @Override
    public Future<String> deployJerseyVerticle() {
        return deployJerseyVerticle("java-guice:" + JerseyVerticle.class.getName());
    }
}
