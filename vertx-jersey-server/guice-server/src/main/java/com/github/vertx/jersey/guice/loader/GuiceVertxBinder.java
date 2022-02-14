package com.github.vertx.jersey.guice.loader;

import com.google.inject.AbstractModule;
import io.vertx.core.Vertx;

public class GuiceVertxBinder extends AbstractModule {

  private final Vertx vertx;

  public GuiceVertxBinder(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void configure() {
    bind(Vertx.class).toInstance(vertx);
  }
}