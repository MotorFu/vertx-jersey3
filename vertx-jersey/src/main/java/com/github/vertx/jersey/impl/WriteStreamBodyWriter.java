package com.github.vertx.jersey.impl;

import com.github.vertx.jersey.WriteStreamOutput;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Jersey {@link jakarta.ws.rs.ext.MessageBodyWriter} for {@link com.github.vertx.jersey.WriteStreamOutput}
 */
public class WriteStreamBodyWriter implements MessageBodyWriter<WriteStreamOutput> {

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return WriteStreamOutput.class.isAssignableFrom(type);
  }

  @Override
  public long getSize(WriteStreamOutput writeStreamOutput, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
    return -1;
  }

  @Override
  public void writeTo(WriteStreamOutput writeStreamOutput, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
    // Do nothing
  }
}
