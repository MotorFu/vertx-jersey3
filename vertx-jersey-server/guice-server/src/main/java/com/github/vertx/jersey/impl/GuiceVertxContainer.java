package com.github.vertx.jersey.impl;

import com.github.vertx.jersey.ApplicationConfigurator;
import com.github.vertx.jersey.JerseyOptions;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.inject.Nullable;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import io.vertx.core.Vertx;
import org.glassfish.jersey.internal.inject.InjectionManager;

/**
 * @author fufengming
 * @email ffu@leapcloud.cn
 * @since 2022/2/21 17:07
 */
@Singleton
public class GuiceVertxContainer extends DefaultVertxContainer {
  @Inject
  public GuiceVertxContainer(Vertx vertx, JerseyServerOptions serverOptions, JerseyOptions options,
                             @Nullable Provider<InjectionManager> injectionManager,
                             @Nullable ApplicationConfigurator configurator) {
    super(vertx, serverOptions, options, injectionManager, configurator);
  }
}
