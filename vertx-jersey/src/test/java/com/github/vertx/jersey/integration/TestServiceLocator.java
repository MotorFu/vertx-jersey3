package com.github.vertx.jersey.integration;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;

/**
 * DI helper for integration tests
 */
public interface TestServiceLocator {

    void init(Vertx vertx);

    void tearDown();

    default JsonObject getConfig() {
        return ConfigUtils.loadConfig();
    }

    <T> T getService(Class<T> clazz);

    default HttpClient getHttpClient() {
        return getVertx().createHttpClient();
    }
//
    default Vertx getVertx() {
        return getService(Vertx.class);
    }

    Future<String> deployJerseyVerticle();

    default Future<String> deployJerseyVerticle(String identifier) {
        DeploymentOptions options = new DeploymentOptions().setConfig(getConfig());
        return getVertx().deployVerticle(identifier, options);
    }

}
