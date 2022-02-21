package com.github.vertx.jersey.hk2;

import com.github.vertx.jersey.JerseyHandler;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.VertxContainer;
import com.github.vertx.jersey.impl.DefaultJerseyServer;
import jakarta.inject.Inject;

public class HK2JerseyServer extends DefaultJerseyServer {
  @Inject
  public HK2JerseyServer(JerseyHandler jerseyHandler, VertxContainer container, JerseyServerOptions serverOptions) {
    super(jerseyHandler, container, serverOptions);
  }
}
