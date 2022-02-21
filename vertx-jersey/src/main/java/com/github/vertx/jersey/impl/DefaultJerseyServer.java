/*
 * The MIT License (MIT)
 * Copyright © 2013 Englishtown <opensource@englishtown.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.vertx.jersey.impl;

import com.github.vertx.jersey.JerseyHandler;
import com.github.vertx.jersey.JerseyOptions;
import com.github.vertx.jersey.JerseyServer;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.VertxContainer;
import com.github.vertx.jersey.inject.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import jakarta.inject.Inject;


/**
 * Default implementation of {@link JerseyServer}
 */
public class DefaultJerseyServer implements JerseyServer {

  private static final Logger logger = LoggerFactory.getLogger(DefaultJerseyServer.class);

  private JerseyHandler jerseyHandler;
  private VertxContainer container;
  private final JerseyServerOptions optionsProvider;
  private Handler<HttpServer> setupHandler;
  private HttpServer server;

  @Inject
  public DefaultJerseyServer(JerseyHandler jerseyHandler,
                             VertxContainer container,
                             JerseyServerOptions optionsProvider) {
    this.jerseyHandler = jerseyHandler;
    this.container = container;
    this.optionsProvider = optionsProvider;
  }

  @Override
  public void start(@Nullable JerseyServerOptions options, @Nullable JerseyOptions jerseyOptions, @Nullable Handler<AsyncResult<HttpServer>> doneHandler) {

    if (options == null) {
      options = optionsProvider;
    }

    HttpServerOptions serverOptions = options.getServerOptions();
    if (serverOptions == null) {
      throw new IllegalArgumentException("http server options cannot be null");
    }

    // Create container and set options if provided
    if (jerseyOptions != null) {
      container.setOptions(jerseyOptions);
    }

    // Create handler and set container
    jerseyHandler.setContainer(container);

    // Create the http server
    server = container.getVertx().createHttpServer(serverOptions);

    // Set request handler for the baseUri
    server.requestHandler(jerseyHandler);

    // Perform any additional server setup (add routes etc.)
    if (setupHandler != null) {
      setupHandler.handle(server);
    }
    container.getVertx().executeBlocking(
      future -> {
        // Run container startup
        container.start();
        future.complete();
      },
      result -> {
        if (result.failed()) {
          logger.error("Failed to start the jersey container", result.cause());
          if (doneHandler != null) {
            doneHandler.handle(Future.failedFuture(result.cause()));
          }
          return;
        }

        // Start listening and log success/failure
        server.listen(ar -> {
          final String listenPath = (serverOptions.isSsl() ? "https" : "http") + "://" + serverOptions.getHost() + ":" + serverOptions.getPort();
          if (ar.succeeded()) {
            logger.info("Http server listening for " + listenPath);
          } else {
            logger.error("Failed to start http server listening for " + listenPath, ar.cause());
          }
          if (doneHandler != null) {
            doneHandler.handle(ar);
          }
        });
      });

  }

  /**
   * Allows custom setup during initialization before the http server is listening
   *
   * @param handler the handler invoked with the http server
   */
  @Override
  public void setupHandler(Handler<HttpServer> handler) {
    this.setupHandler = handler;
  }

  /**
   * Returns the JerseyHandler instance for the JerseyServer
   *
   * @return the JerseyHandler instance
   */
  @Override
  public JerseyHandler getHandler() {
    return jerseyHandler;
  }

  /**
   * Returns the internal vert.x {@link io.vertx.core.http.HttpServer}
   *
   * @return the vert.x http server instance
   */
  @Override
  public HttpServer getHttpServer() {
    return server;
  }

  /**
   * Shutdown jersey server and release resources
   */
  @Override
  public void stop() {
    // Run jersey shutdown lifecycle
    if (container != null) {
      container.stop();
      container = null;
    }
    // Destroy the jersey service locator
    if (jerseyHandler != null && jerseyHandler.getDelegate() != null) {
//      jerseyHandler.getDelegate().getInjectionManager()
      jerseyHandler = null;
    }
    if (server != null) {
      server.close();
      server = null;
    }
  }
}
