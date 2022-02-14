package com.github.vertx.jersey.impl;

import com.github.vertx.jersey.WriteStreamOutput;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerResponse;

/**
 * Default implementation of {@link com.github.vertx.jersey.WriteStreamOutput}
 */
public class DefaultWriteStreamOutput implements WriteStreamOutput {

  private HttpServerResponse response;
  private Handler<Void> endHandler;

  /**
   * {@inheritDoc}
   */
  @Override
  public WriteStreamOutput init(HttpServerResponse response, Handler<Void> endHandler) {
    this.response = response;
    this.endHandler = endHandler;
    return this;
  }

  /**
   * Flag to indicate if the {@link io.vertx.core.http.HttpServerResponse} has been set yet.
   *
   * @return boolean flag
   */
  @Override
  public boolean isResponseSet() {
    return (response != null);
  }

  @Override
  public void end(Handler<AsyncResult<Void>> handler) {
    checkResponseSet();
    if (endHandler != null) {
      endHandler.handle(null);
    } else {
      response.end();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @return
   */
  @Override
  public Future<Void> write(Buffer data) {
    checkResponseSet();
    return response.write(data);
  }

  @Override
  public void write(Buffer buffer, Handler<AsyncResult<Void>> handler) {
    checkResponseSet();
    response.write(buffer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WriteStreamOutput setWriteQueueMaxSize(int maxSize) {
    checkResponseSet();
    response.setWriteQueueMaxSize(maxSize);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean writeQueueFull() {
    checkResponseSet();
    return response.writeQueueFull();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WriteStreamOutput drainHandler(Handler<Void> handler) {
    checkResponseSet();
    response.drainHandler(handler);
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public WriteStreamOutput exceptionHandler(Handler<Throwable> handler) {
    checkResponseSet();
    response.exceptionHandler(handler);
    return this;
  }

  private void checkResponseSet() {
    if (response == null) {
      throw new IllegalStateException("The HttpServerResponse has not been set yet.");
    }
  }

}
