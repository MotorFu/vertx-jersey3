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

package com.github.vertx.jersey.inject.impl;

import com.github.vertx.jersey.impl.VertxResponseWriter;
import com.github.vertx.jersey.inject.ContainerResponseWriterProvider;
import com.github.vertx.jersey.inject.VertxPostResponseProcessor;
import com.github.vertx.jersey.inject.VertxResponseProcessor;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Default implementation of ContainerResponseWriterProvider
 */
@Singleton
public class VertxResponseWriterProvider implements ContainerResponseWriterProvider {

  private final Vertx vertx;
  private final List<VertxResponseProcessor> responseProcessors;
  private final List<VertxPostResponseProcessor> postResponseProcessors;

  @Inject
  public VertxResponseWriterProvider(
    Vertx vertx,
    List<VertxResponseProcessor> responseProcessors,
    List<VertxPostResponseProcessor> postResponseProcessors) {
    this.vertx = vertx;
    this.responseProcessors = responseProcessors;
    this.postResponseProcessors = postResponseProcessors;
  }

  @Override
  public ContainerResponseWriter get(
    HttpServerRequest vertxRequest,
    ContainerRequest jerseyRequest) {
    return new VertxResponseWriter(vertxRequest, vertx, responseProcessors, postResponseProcessors);
  }

}
