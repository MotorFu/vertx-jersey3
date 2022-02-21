package com.englishtown.vertx.jersey.examples.integration;

import com.github.vertx.jersey.examples.guice.inject.GuiceExceptionMapper;
import com.github.vertx.guice.integration.JerseyGuiceIntegrationTestBase;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import org.junit.Test;


public class GuiceIntegrationTest extends JerseyGuiceIntegrationTestBase {

    private String BASE_PATH = "http://localhost:8080/guice";

    @Test
    public void testGet() throws Exception {


        httpRequest(HttpMethod.GET, BASE_PATH, ar1->{
          if(ar1.succeeded()){
            ar1.result().send(ar2->{
              if(ar2.succeeded()){
                final HttpClientResponse response = ar2.result();
                assertEquals(200, response.statusCode());
                testComplete();
              } else {
                this.onRejected(ar1.cause());
              }
            });
          } else {
            this.onRejected(ar1.cause());
          }
        });

        await();

    }

    @Test
    public void testExceptionMapper() throws Exception {
      httpRequest(HttpMethod.GET, BASE_PATH+ "/exception", ar1->{
        if(ar1.succeeded()){
          ar1.result().send(ar2->{
            if(ar2.succeeded()){
              final HttpClientResponse response = ar2.result();
              assertEquals(GuiceExceptionMapper.STATUS_IM_A_TEAPOT.getStatusCode(), response.statusCode());
              assertEquals(MediaType.APPLICATION_JSON, response.getHeader(HttpHeaders.CONTENT_TYPE));

              response.body(ar3->{
                if(ar3.succeeded()){
                  JsonObject json = ar3.result().toJsonObject();
                  assertTrue(json.containsKey("msg"));
                  assertTrue(json.containsKey("type"));
                  testComplete();
                } else {
                  this.onRejected(ar1.cause());
                }
              });

            } else {
              this.onRejected(ar1.cause());
            }
          });
        } else {
          this.onRejected(ar1.cause());
        }
      });

      await();

    }

}