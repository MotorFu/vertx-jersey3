package com.github.vertx.jersey.impl;

import com.github.vertx.jersey.ApplicationConfigurator;
import com.github.vertx.jersey.JerseyOptions;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.inject.Nullable;
import io.vertx.core.Vertx;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.spi.Container;
import org.jvnet.hk2.annotations.Optional;

/**
 * Vert.x implementation of {@link Container}
 */
public class HK2VertxContainer extends DefaultVertxContainer {

  @Inject
  public HK2VertxContainer(Vertx vertx,
                           JerseyServerOptions serverOptions,
                           JerseyOptions options,
                           @Nullable Provider<InjectionManager> injectionManager,
                           @Optional @Nullable ApplicationConfigurator configurator) {
    super(vertx, serverOptions, options, injectionManager, configurator);
  }

}
