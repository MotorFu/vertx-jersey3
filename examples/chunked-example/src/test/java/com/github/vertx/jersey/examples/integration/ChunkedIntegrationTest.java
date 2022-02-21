package com.github.vertx.jersey.examples.integration;

import com.github.vertx.hk2.integration.JerseyHK2IntegrationTestBase;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChunkedIntegrationTest extends JerseyHK2IntegrationTestBase {
  private static Logger logger = LoggerFactory.getLogger(DemoTest.class);
  private String BASE_PATH = "http://localhost:8080/chunked";

  private void runTest(String path) {

    httpRequest(HttpMethod.GET, path, ar1 -> {
      if (ar1.succeeded()) {
        ar1.result()
          .send(ar2 -> {
            final HttpClientResponse response = ar2.result();
            assertEquals(200, response.statusCode());
            assertEquals("chunked", response.headers().get(HttpHeaderNames.TRANSFER_ENCODING));
            if (ar2.succeeded()) {
              ar2.result().body(ar3 -> {
                if (ar3.succeeded()) {
                  String text = ar3.result().toString();
                  logger.info("result--->{}", text);
                  assertTrue(text.startsWith("aaaaa"));
                  testComplete();
                } else {
                  this.onRejected(ar2.cause());
                }
              });
            } else {
              this.onRejected(ar2.cause());
            }
          });
      } else {
        this.onRejected(ar1.cause());
      }
    });
    await();

  }

  @Test
  public void testGetChunkedString() throws Exception {
    runTest(BASE_PATH);
  }

  @Test
  public void testGetStringAsync() throws Exception {
    runTest(BASE_PATH + "/async");
  }

  @Test
  public void testGetStream() throws Exception {

    RequestOptions options = new RequestOptions();

    options.setMethod(HttpMethod.GET);
    options.setURI(BASE_PATH + "/stream");
    getHttpClient().request(options, asyncResult -> {
      final Future<HttpClientResponse> response = asyncResult.result().response();
      assertEquals(200, response.result().statusCode());
      assertNull(response.result().headers().get(HttpHeaderNames.TRANSFER_ENCODING));
      assertEquals("chunked", response.result().headers().get(HttpHeaderNames.TRANSFER_ENCODING));
      assertEquals("36", response.result().headers().get(HttpHeaderNames.CONTENT_LENGTH));

      response.onSuccess(res -> {
        res.body(it -> {
          Buffer body = it.result();
          String text = body.toString();
          assertEquals("abcdefghijklmnopqrstuvwxyz0123456789", text);
          testComplete();
        }).end();
      }).onFailure(this::onRejected);

    });

    await();
  }
}