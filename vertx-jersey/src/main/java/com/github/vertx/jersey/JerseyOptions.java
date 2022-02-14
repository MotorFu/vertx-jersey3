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

package com.github.vertx.jersey;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Provides configuration for a {@link JerseyHandler}
 */
public interface JerseyOptions {

  /**
   * @param config
   * @param vertx
   * @deprecated Perform initialization at construction time
   */
  @Deprecated
  default void init(JsonObject config, Vertx vertx) {
    throw new UnsupportedOperationException();
  }

  /**
   * @param config the underlying configuration settings
   * @deprecated Perform initialization at construction time
   */
  @Deprecated
  default void init(JsonObject config) {
    throw new UnsupportedOperationException();
  }

  /**
   * Returns a list of packages to be scanned for resources and components
   *
   * @return
   */
  List<String> getPackages();

  /**
   * Optional additional properties to be applied to Jersey resource configuration
   *
   * @return
   */
  Map<String, Object> getProperties();

  /**
   * Optional list of components to be registered (features etc.)
   *
   * @return
   */
  Set<Class<?>> getComponents();

  /**
   * Optional list of singleton instances to be registered (hk2 binders etc.)
   *
   * @return
   */
  Set<Object> getInstances();

  /**
   * Returns the base URI used by Jersey
   *
   * @return base URI
   */
  URI getBaseUri();

  /**
   * The max body size in bytes when reading the vert.x input stream
   *
   * @return the max body size bytes
   */
  int getMaxBodySize();

}