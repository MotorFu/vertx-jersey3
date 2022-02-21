package com.github.vertx.guice.integration;

import com.github.vertx.jersey.integration.JerseyIntegrationTestBase;

/**
 * Base class for jersey integration tests
 */
public abstract class JerseyGuiceIntegrationTestBase extends JerseyIntegrationTestBase {

    protected JerseyGuiceIntegrationTestBase() {
        super(new GuiceTestServiceLocator());
    }

}
