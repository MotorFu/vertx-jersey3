package com.github.vertx.jersey.integration;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JdkSSLEngineOptions;
import io.vertx.test.core.VertxTestBase;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Base integration test class
 */
public abstract class JerseyIntegrationTestBase extends VertxTestBase {

  private final TestServiceLocator locator;
  protected HttpClient httpClient;
  protected RequestOptions options;
  protected String deploymentID;

  protected JerseyIntegrationTestBase(TestServiceLocator locator) {
    this.locator = locator;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    init();
  }

  protected void init() throws Exception {
    final JsonObject config = ConfigUtils.loadConfig();
    locator.init(vertx);

    HttpClientOptions clientOptions = new HttpClientOptions()
      .setConnectTimeout(1000)
      .setSslEngineOptions(new JdkSSLEngineOptions())
      .setUseAlpn(false)
      .setSsl(false)
      .setProtocolVersion(HttpVersion.HTTP_1_1);
    httpClient = vertx.createHttpClient(clientOptions);

    options = new RequestOptions()
      .setHost(config.getString("host", "localhost"))
      .setPort(config.getInteger("port", 8080));

    CompletableFuture<String> future = new CompletableFuture<>();

    locator.deployJerseyVerticle()
      .onComplete(res -> {
        if (res.succeeded()) {
          future.complete(res.result());
        } else {
          future.completeExceptionally(res.cause());
        }
      })
      .otherwise(t -> {
        future.completeExceptionally(t);
        return null;
      });
    deploymentID = future.get(10, TimeUnit.SECONDS);
  }

  @Override
  protected void tearDown() throws Exception {
    httpClient.close();
    locator.tearDown();
    vertx.close();
    super.tearDown();
  }

  protected Promise<Void> onRejected(Throwable t) {
    t.printStackTrace();
    fail();
    return null;
  }

  protected RequestOptions getRequestOptions() {
    return options;
  }

  protected HttpClient getHttpClient() {
    return httpClient;
  }

  protected void httpRequest(HttpMethod method, String path, Handler<AsyncResult<HttpClientRequest>> handler) {
    options.setMethod(method).setURI(path);
    httpClient.request(options, handler);
  }


}
