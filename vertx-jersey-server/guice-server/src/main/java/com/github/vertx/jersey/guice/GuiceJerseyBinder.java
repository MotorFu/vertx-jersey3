package com.github.vertx.jersey.guice;

import com.github.vertx.jersey.ApplicationConfigurator;
import com.github.vertx.jersey.JerseyHandler;
import com.github.vertx.jersey.JerseyOptions;
import com.github.vertx.jersey.JerseyServer;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.VertxContainer;
import com.github.vertx.jersey.impl.DefaultJerseyHandler;
import com.github.vertx.jersey.impl.DefaultJerseyOptions;
import com.github.vertx.jersey.impl.GuiceVertxContainer;
import com.github.vertx.jersey.impl.WriteStreamBodyWriter;
import com.github.vertx.jersey.inject.ContainerResponseWriterProvider;
import com.github.vertx.jersey.inject.VertxPostResponseProcessor;
import com.github.vertx.jersey.inject.VertxRequestProcessor;
import com.github.vertx.jersey.inject.VertxResponseProcessor;
import com.github.vertx.jersey.inject.impl.VertxResponseWriterProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.util.Providers;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Guice Jersey binder
 */
public class GuiceJerseyBinder extends AbstractModule {

//  private final ServiceLocator locator;
//  public GuiceJerseyBinder(ServiceLocator locator){
//    this.locator=locator;
//  }
  /**
   * Configures a {@link com.google.inject.Binder} via the exposed methods.
   */
  @Override
  protected void configure() {

    // Create Guice service locator and bind jersey injections
//    ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);
//
//    bind(ServiceLocator.class).toInstance(locator);


    final InjectionManager injectionManager = Injections.createInjectionManager(null);

    bind(InjectionManager.class).toInstance(injectionManager);
    bind(JerseyServer.class).to(GuiceJerseyServer.class);
    bind(JerseyHandler.class).to(DefaultJerseyHandler.class);
    bind(JerseyOptions.class).to(DefaultJerseyOptions.class);
    bind(JerseyServerOptions.class).to(DefaultJerseyOptions.class);
    bind(VertxContainer.class).to(GuiceVertxContainer.class);
    bind(ContainerResponseWriterProvider.class).to(VertxResponseWriterProvider.class);
    bind(MessageBodyWriter.class).to(WriteStreamBodyWriter.class).in(Scopes.SINGLETON);
    bind(ApplicationConfigurator.class).toProvider(Providers.of(null));

    Multibinder.newSetBinder(binder(), VertxRequestProcessor.class);
    Multibinder.newSetBinder(binder(), VertxResponseProcessor.class);
    Multibinder.newSetBinder(binder(), VertxPostResponseProcessor.class);

    Multibinder.newSetBinder(binder(), ContainerRequestFilter.class);
    Multibinder.newSetBinder(binder(), ContainerResponseFilter.class);
    Multibinder.newSetBinder(binder(), ReaderInterceptor.class);
    Multibinder.newSetBinder(binder(), WriterInterceptor.class);
    Multibinder.newSetBinder(binder(), ModelProcessor.class);
    Multibinder.newSetBinder(binder(), ContainerLifecycleListener.class);
    Multibinder.newSetBinder(binder(), ApplicationEventListener.class);
    Multibinder.newSetBinder(binder(), ExceptionMapper.class);

  }

  @Provides
  List<VertxRequestProcessor> provideVertxRequestProcessorList(Set<VertxRequestProcessor> processors) {
    return new ArrayList<>(processors);
  }

  @Provides
  List<VertxResponseProcessor> provideVertxResponseProcessorList(Set<VertxResponseProcessor> processors) {
    return new ArrayList<>(processors);
  }

  @Provides
  List<VertxPostResponseProcessor> provideVertxPostResponseProcessorList(Set<VertxPostResponseProcessor> processors) {
    return new ArrayList<>(processors);
  }

}
