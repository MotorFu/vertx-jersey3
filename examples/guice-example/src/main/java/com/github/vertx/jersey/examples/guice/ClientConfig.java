package com.github.vertx.jersey.examples.guice;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author fufengming
 * @email ffu@maxleap.com
 * @since 2018/6/25 19:35
 */
public class ClientConfig {
  private static Logger logger = LoggerFactory.getLogger(ClientConfig.class);


  public static JsonObject localConfig() {
    JsonObject jsonObject = null;
    String filename = "config.json";
    InputStream is = null;
    try {
      //获取jar包外部配置，server.json与jar包同级
      try {
        is = new FileInputStream(filename);
      } catch (Exception e) {
        logger.warn("jar外部没有发现client.json");
      }
      if (is == null) {
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
      }
      if (is != null) {
        Buffer buffer = Buffer.buffer(toByteArray(is));
        jsonObject = new JsonObject(Json.decodeValue(buffer, Map.class));
        logger.info(String.format("FileInputStream %s", jsonObject));
        return jsonObject;
      } else {
        logger.info("config.json不存在");
      }
    } catch (Exception e) {
      logger.error("没有发现client.json", e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  public static byte[] toByteArray(InputStream input) throws IOException {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buffer = new byte[4096];
    int n = 0;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
    }
    return output.toByteArray();
  }
}
