package com.github.vertx.jersey.examples;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

/**
 * @author fufengming
 * @email ffu@leapcloud.cn
 * @since 2022/2/16 18:38
 */
public class Bootstrap {
  private static Logger logger = LoggerFactory.getLogger(ClientConfig.class);
  public static void main(String[] args) {
    VertxOptions vertxOptions = new VertxOptions();
    vertxOptions.setBlockedThreadCheckInterval(100000000);
    Vertx vertx = Vertx.vertx(vertxOptions);
    DeploymentOptions options = new DeploymentOptions();
    options.setConfig(ClientConfig.localConfig());
    vertx.deployVerticle("java-hk2:com.github.vertx.jersey.JerseyVerticle", options, event -> {
      if (event.succeeded()) {
        logger.info("success");
      } else {
        logger.error(event.cause());
      }
    });
  }
}
