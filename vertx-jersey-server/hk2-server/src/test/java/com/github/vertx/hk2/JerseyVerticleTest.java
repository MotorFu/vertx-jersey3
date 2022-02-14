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

import com.github.vertx.jersey.JerseyOptions;
import com.github.vertx.jersey.JerseyServer;
import com.github.vertx.jersey.JerseyVerticle;
import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

/**
 * {@link JerseyVerticle} unit testsany
 */
public class JerseyVerticleTest {

    JerseyVerticle jerseyVerticle;
    JsonObject config = new JsonObject();

    @Mock
    public Vertx vertx;
    @Mock
    public Context context;
    @Mock
    public JerseyServer jerseyServer;
    @Mock
    public JerseyOptions options;
    @Mock
    public Promise<Void> startedResult;
    @Mock
    public AsyncResult<HttpServer> asyncResult;
    @Captor
    public ArgumentCaptor<Handler<AsyncResult<HttpServer>>> handlerCaptor;
    @Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
        when(vertx.getOrCreateContext()).thenReturn(context);
        when(context.config()).thenReturn(config);
        jerseyVerticle = new JerseyVerticle(jerseyServer, options);
        jerseyVerticle.init(vertx, context);
    }

    @Test
    public void testStart() throws Exception {

        jerseyVerticle.start(startedResult);

        verify(startedResult, never()).complete();
        verify(startedResult, never()).fail(any(Throwable.class));

        verify(jerseyServer).start(handlerCaptor.capture());

        when(asyncResult.succeeded()).thenReturn(true).thenReturn(false);

        handlerCaptor.getValue().handle(asyncResult);
        verify(startedResult).complete();
        verify(startedResult, never()).fail(any(Throwable.class));

        handlerCaptor.getValue().handle(asyncResult);
        verify(startedResult).complete();
        verify(startedResult).fail(any(Throwable.class));

    }

}
