package com.github.vertx.jersey.hk2.loader;


import io.vertx.core.Vertx;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.internal.inject.InjectionManager;

/**
 * @author fufengming
 * @email ffu@leapcloud.cn
 * @since 2022/2/11 18:40
 */
public class HK2VertxBinder extends AbstractBinder {
  private final Vertx vertx;

  private InjectionManager injectionManager;

  public HK2VertxBinder(Vertx vertx) {
    this.vertx = vertx;
  }

  public HK2VertxBinder(Vertx vertx, InjectionManager injectionManager) {
    this.vertx = vertx;
    this.injectionManager = injectionManager;
  }

  /**
   * Implement to provide binding definitions using the exposed binding
   * methods.
   */
  @Override
  protected void configure() {
    this.bind(this.vertx).to(Vertx.class);
    if (this.injectionManager != null) {
      this.bind(this.injectionManager).to(InjectionManager.class);
    }
  }
}
