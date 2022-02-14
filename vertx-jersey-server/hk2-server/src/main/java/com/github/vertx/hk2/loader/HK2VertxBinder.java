package com.github.vertx.hk2.loader;


import io.vertx.core.Vertx;
import org.glassfish.jersey.internal.inject.AbstractBinder;

/**
 * @author fufengming
 * @email ffu@leapcloud.cn
 * @since 2022/2/11 18:40
 */
public class HK2VertxBinder extends AbstractBinder {
  private final Vertx vertx;

  public HK2VertxBinder(Vertx vertx){
    this.vertx=vertx;
  }
  /**
   * Implement to provide binding definitions using the exposed binding
   * methods.
   */
  @Override
  protected void configure() {
    this.bind(this.vertx).to(Vertx.class);
  }
}
