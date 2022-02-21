package com.github.vertx.jersey.guice;

import com.github.vertx.jersey.JerseyHandler;
import com.github.vertx.jersey.JerseyServerOptions;
import com.github.vertx.jersey.VertxContainer;
import com.github.vertx.jersey.impl.DefaultJerseyServer;
import com.google.inject.Inject;

/**
 * Guice extension of {@link com.github.vertx.jersey.impl.DefaultJerseyServer}
 */
public class GuiceJerseyServer extends DefaultJerseyServer {

  @Inject
  public GuiceJerseyServer(JerseyHandler jerseyHandler, VertxContainer container, JerseyServerOptions optionsProvider) {
    super(jerseyHandler, container, optionsProvider);
//    initBridge(locator, injector);
  }

  /**
   * Initialize the hk2 bridge
   *
   * @param locator  the HK2 locator
   * @param injector the Guice injector
   */
//  public static void initBridge(ServiceLocator locator, Injector injector) {

    // Set up bridge
//    GuiceBridge.getGuiceBridge().initializeGuiceBridge(locator);
//    GuiceIntoHK2Bridge guiceBridge = locator.getService(GuiceIntoHK2Bridge.class);
//    guiceBridge.bridgeGuiceInjector(injector);
//    injectMultibindings(locator, injector);
//
//    // Bind guice scope context
//    ServiceLocatorUtilities.bind(locator, new AbstractBinder() {
//      @Override
//      protected void configure() {
//        bind(GuiceScopeContext.class).to(new TypeLiteral<Context<GuiceScope>>() {}).in(Singleton.class);
////        bindAsContract(GuiceScopeContext.class)
////          .to(new TypeLiteral<Context<GuiceScope>>() {}.getType())
////          .in(Singleton.class);
//      }
//    });
//  }


  /**
   * This is a workaround for the hk2 bridge limitations
   *
   * @param locator  the HK2 locator
   * @param injector the Guice injector
   */
//  protected static void injectMultibindings(ServiceLocator locator, Injector injector) {
//
//    injectMultiBindings(locator, injector, new Key<Set<ContainerRequestFilter>>() {
//    }, ContainerRequestFilter.class);
//    injectMultiBindings(locator, injector, new Key<Set<ContainerResponseFilter>>() {
//    }, ContainerResponseFilter.class);
//    injectMultiBindings(locator, injector, new Key<Set<ReaderInterceptor>>() {
//    }, ReaderInterceptor.class);
//    injectMultiBindings(locator, injector, new Key<Set<WriterInterceptor>>() {
//    }, WriterInterceptor.class);
//    injectMultiBindings(locator, injector, new Key<Set<ModelProcessor>>() {
//    }, ModelProcessor.class);
//    injectMultiBindings(locator, injector, new Key<Set<ContainerLifecycleListener>>() {
//    }, ContainerLifecycleListener.class);
//    injectMultiBindings(locator, injector, new Key<Set<ApplicationEventListener>>() {
//    }, ApplicationEventListener.class);
//    injectMultiBindings(locator, injector, new Key<Set<ExceptionMapper>>() {
//    }, ExceptionMapper.class);
//
//  }
//
//  protected static void injectMultiBindings(ServiceLocator locator, Injector injector, Key<? extends Set<?>> key, Type type) {
//
//    Set<?> set = injector.getInstance(key);
//
//    if (set != null && !set.isEmpty()) {
//      for (Object obj : set) {
//        ServiceLocatorUtilities.addOneConstant(locator, obj, null, type);
//      }
//    }
//
//  }

}
