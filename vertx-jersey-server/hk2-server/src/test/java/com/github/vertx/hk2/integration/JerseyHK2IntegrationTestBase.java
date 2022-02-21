package com.github.vertx.hk2.integration;

import com.github.vertx.jersey.integration.JerseyIntegrationTestBase;

/**
 * Base class for jersey integration tests
 */
public abstract class JerseyHK2IntegrationTestBase extends JerseyIntegrationTestBase {

    public JerseyHK2IntegrationTestBase() {
        super(new HK2TestServiceLocator());
    }

}
