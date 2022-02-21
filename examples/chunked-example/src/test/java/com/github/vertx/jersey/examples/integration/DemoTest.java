package com.github.vertx.jersey.examples.integration;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.net.JdkSSLEngineOptions;
import io.vertx.test.core.VertxTestBase;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fufengming
 * @email ffu@leapcloud.cn
 * @since 2022/2/17 15:48
 */

public class DemoTest extends VertxTestBase {
  private static Logger logger = LoggerFactory.getLogger(DemoTest.class);

  HttpClient client;


  @Before
  public void setUp() throws Exception {
    super.setUp();
    HttpClientOptions clientOptions = new HttpClientOptions()
      .setSslEngineOptions(new JdkSSLEngineOptions())
      .setUseAlpn(false)
      .setSsl(false)
      .setProtocolVersion(HttpVersion.HTTP_1_1);
    client = vertx.createHttpClient(clientOptions);
  }

  @Test
  public void test() {
    logger.error("test");

    RequestOptions options = new RequestOptions()
      .setHost("localhost")
      .setPort(8080)
      .setMethod(HttpMethod.GET)
      .setURI("/chunked");
    client.request(options, ar1 -> {
      if (ar1.succeeded()) {
        HttpClientRequest request = ar1.result();
        request.send(ar2 -> {
          if (ar2.succeeded()) {
            HttpClientResponse response = ar2.result();
            assertEquals(200, response.statusCode());
            assertEquals("chunked", response.headers().get(HttpHeaderNames.TRANSFER_ENCODING));
            response.body(ar3 -> {
              if (ar3.succeeded()) {
                String text = ar3.result().toString();
                logger.info("result--->{}", text);
                assertTrue(text.startsWith("aaaaa"));
                testComplete();
              } else {
                logger.error("err3", ar3.cause());
              }
            });
          } else {
            logger.error("err2", ar2.cause());
          }
        });
      } else {
        logger.error("err1", ar1.cause());
      }

    });

    await();
  }

  @Test
  public void test2() {
    client.request(HttpMethod.GET, 8080, "localhost", "/chunked", ar1 -> {
      if (ar1.succeeded()) {
        HttpClientRequest request = ar1.result();

        // Send the request and process the response
        request.send(ar -> {
          if (ar.succeeded()) {
            HttpClientResponse response = ar.result();
            logger.info("Received response with status code " + response.statusCode());
            response.body(ar3 -> {
              if (ar3.succeeded()) {
                logger.info("Received response body " + ar3.result());
              }
              testComplete();
            });
          } else {
            logger.error("Something went wrong " + ar.cause().getMessage());
          }
        });
      }
    });
    await();
  }

  @Test
  public void test3() {
    client.request(HttpMethod.GET, 8080, "localhost", "/chunked", ar1 -> {
      if (ar1.succeeded()) {
        ar1.result()
          .send()
          .onComplete(ar2->{
            ar2.result().body(ar3->{
              logger.info("Received response body " + ar3.result());
              testComplete();
            });
          });
      }
    });
    await();
  }
}
