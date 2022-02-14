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

package com.github.vertx.hk2;

import com.github.vertx.jersey.*;
import com.github.vertx.jersey.impl.*;
import com.github.vertx.jersey.inject.ContainerResponseWriterProvider;
import com.github.vertx.jersey.inject.VertxPostResponseProcessor;
import com.github.vertx.jersey.inject.VertxRequestProcessor;
import com.github.vertx.jersey.inject.VertxResponseProcessor;
import com.github.vertx.jersey.inject.impl.VertxResponseWriterProvider;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.ext.MessageBodyWriter;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * HK2 Jersey binder
 */
public class HK2JerseyBinder extends AbstractBinder {


  static class VertxRequestProcessorFactory implements Factory<List<VertxRequestProcessor>> {

    private final List<VertxRequestProcessor> processors = new ArrayList<>();

    @Inject
    public VertxRequestProcessorFactory(IterableProvider<VertxRequestProcessor> providers) {
      for (VertxRequestProcessor processor : providers) {
        processors.add(processor);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VertxRequestProcessor> provide() {
      return processors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(List<VertxRequestProcessor> instance) {
    }
  }

  static class VertxResponseProcessorFactory implements Factory<List<VertxResponseProcessor>> {

    private final List<VertxResponseProcessor> processors = new ArrayList<>();

    @Inject
    public VertxResponseProcessorFactory(IterableProvider<VertxResponseProcessor> providers) {
      for (VertxResponseProcessor processor : providers) {
        processors.add(processor);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VertxResponseProcessor> provide() {
      return processors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(List<VertxResponseProcessor> instance) {
    }
  }

  static class VertxPostResponseProcessorFactory implements Factory<List<VertxPostResponseProcessor>> {

    private final List<VertxPostResponseProcessor> processors = new ArrayList<>();

    @Inject
    public VertxPostResponseProcessorFactory(IterableProvider<VertxPostResponseProcessor> providers) {
      for (VertxPostResponseProcessor processor : providers) {
        processors.add(processor);
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VertxPostResponseProcessor> provide() {
      return processors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose(List<VertxPostResponseProcessor> instance) {
    }
  }

  /**
   * Implement to provide binding definitions using the exposed binding
   * methods.
   */
  @Override
  protected void configure() {

    bind(DefaultVertxContainer.class).to(VertxContainer.class);
    bind(DefaultJerseyServer.class).to(JerseyServer.class);
    bind(DefaultJerseyHandler.class).to(JerseyHandler.class);
    bind(DefaultJerseyOptions.class).to(JerseyOptions.class).to(JerseyServerOptions.class);
    bind(VertxResponseWriterProvider.class).to(ContainerResponseWriterProvider.class);
    bind(WriteStreamBodyWriter.class).to(MessageBodyWriter.class).in(Singleton.class);

    bindFactory(VertxRequestProcessorFactory.class).to(new TypeLiteral<List<VertxRequestProcessor>>() {
    });
    bindFactory(VertxResponseProcessorFactory.class).to(new TypeLiteral<List<VertxResponseProcessor>>() {
    });
    bindFactory(VertxPostResponseProcessorFactory.class).to(new TypeLiteral<List<VertxPostResponseProcessor>>() {
    });

  }

}
