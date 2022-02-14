package com.github.vertx.jersey.guice;

import com.github.vertx.jersey.JerseyHandler;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.VertxContainer;
import com.github.vertx.jersey.impl.DefaultJerseyServer;
import com.google.inject.Injector;
import com.google.inject.Key;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * Guice extension of {@link com.github.vertx.jersey.impl.DefaultJerseyServer}
 */
public class GuiceJerseyServer extends DefaultJerseyServer {

  @Inject
  public GuiceJerseyServer(JerseyHandler jerseyHandler, VertxContainer container, Provider<JerseyServerOptions> optionsProvider, InjectionManager injectionManager, Injector injector) {
    super(jerseyHandler, container, optionsProvider);
    initBridge(injectionManager, injector);
  }

  /**
   * Initialize the hk2 bridge
   *
   * @param injectionManager  the HK2 locator
   * @param injector the Guice injector
   */
  protected void initBridge(InjectionManager injectionManager, Injector injector) {

    // Set up bridge
    injectMultibindings(injectionManager, injector);

  }

  /**
   * This is a workaround for the hk2 bridge limitations
   *
   * @param locator  the HK2 locator
   * @param injector the Guice injector
   */
  protected void injectMultibindings(InjectionManager locator, Injector injector) {

    injectMultiBindings(locator, injector, new Key<Set<ContainerRequestFilter>>() {
    }, ContainerRequestFilter.class);
    injectMultiBindings(locator, injector, new Key<Set<ContainerResponseFilter>>() {
    }, ContainerResponseFilter.class);
    injectMultiBindings(locator, injector, new Key<Set<ReaderInterceptor>>() {
    }, ReaderInterceptor.class);
    injectMultiBindings(locator, injector, new Key<Set<WriterInterceptor>>() {
    }, WriterInterceptor.class);
    injectMultiBindings(locator, injector, new Key<Set<ModelProcessor>>() {
    }, ModelProcessor.class);
    injectMultiBindings(locator, injector, new Key<Set<ContainerLifecycleListener>>() {
    }, ContainerLifecycleListener.class);
    injectMultiBindings(locator, injector, new Key<Set<ApplicationEventListener>>() {
    }, ApplicationEventListener.class);
    injectMultiBindings(locator, injector, new Key<Set<ExceptionMapper>>() {
    }, ExceptionMapper.class);

  }

  protected void injectMultiBindings(InjectionManager locator, Injector injector, Key<? extends Set<?>> key, Type type) {

    Set<?> set = injector.getInstance(key);

    if (set != null && !set.isEmpty()) {
      for (Object obj : set) {
        locator.register(obj);
      }
    }

  }

}
