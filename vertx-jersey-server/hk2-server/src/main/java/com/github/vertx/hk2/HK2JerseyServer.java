package com.github.vertx.hk2;

import com.github.vertx.jersey.JerseyHandler;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.VertxContainer;
import com.github.vertx.jersey.impl.DefaultJerseyServer;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class HK2JerseyServer extends DefaultJerseyServer {
  @Inject
  public HK2JerseyServer(JerseyHandler jerseyHandler, VertxContainer container, Provider<JerseyServerOptions> optionsProvider) {
    super(jerseyHandler, container, optionsProvider);
  }
}
