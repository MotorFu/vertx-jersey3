package com.github.vertx.hk2.integration;

import com.github.vertx.jersey.hk2.WhenHK2JerseyBinder;
import com.github.vertx.jersey.hk2.loader.HK2VertxBinder;
import com.github.vertx.jersey.JerseyVerticle;
import com.github.vertx.jersey.integration.TestServiceLocator;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/**
 * HK2 implementation of {@link TestServiceLocator}
 */
public class HK2TestServiceLocator implements TestServiceLocator {

  protected ServiceLocator locator;
  protected Vertx vertx;

  @Override
  public void init(Vertx vertx) {
    this.vertx = vertx;

    locator = ServiceLocatorUtilities.bind(
      new HK2VertxBinder(vertx),
      new WhenHK2JerseyBinder());

  }

  @Override
  public void tearDown() {
    if (locator != null) {
      locator.shutdown();
      locator = null;
    }
  }

  @Override
  public <T> T getService(Class<T> clazz) {
    return locator.getService(clazz);
  }

  @Override
  public Future<String> deployJerseyVerticle() {
    DeploymentOptions options = new DeploymentOptions().setConfig(getConfig());
    return vertx.deployVerticle("java-hk2:" + JerseyVerticle.class.getName(), options);
  }
}
