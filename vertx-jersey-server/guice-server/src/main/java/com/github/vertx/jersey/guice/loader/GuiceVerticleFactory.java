package com.github.vertx.jersey.guice.loader;

import com.google.inject.Injector;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

public class GuiceVerticleFactory implements VerticleFactory {

  public static final String PREFIX = "java-guice";

  private Injector injector;

  /**
   * Returns the current parent injector
   *
   * @return
   */
  public Injector getInjector() {
    if (injector == null) {
      injector = createInjector();
    }
    return injector;
  }

  /**
   * Sets the parent injector
   *
   * @param injector
   * @return
   */
  public GuiceVerticleFactory setInjector(Injector injector) {
    this.injector = injector;
    return this;
  }

  @Override
  public String prefix() {
    return PREFIX;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createVerticle(String verticleName, ClassLoader classLoader, Promise<Callable<Verticle>> promise) {
    verticleName = VerticleFactory.removePrefix(verticleName);

    Constructor<Verticle> ctor;
    try {
      // Use the provided class loader to create an instance of GuiceVerticleLoader.  This is necessary when working with vert.x IsolatingClassLoader
      @SuppressWarnings("unchecked")
      Class<Verticle> loader = (Class<Verticle>) classLoader.loadClass(GuiceVerticleLoader.class.getName());
       ctor = loader.getConstructor(String.class, ClassLoader.class, Injector.class);
    } catch (ClassNotFoundException | NoSuchMethodException e) {
      promise.fail(e);
      return;
    }
    String finalVerticleName = verticleName;
    promise.complete(() -> ctor.newInstance(finalVerticleName, classLoader, getInjector()));

  }

  protected Injector createInjector() {
    return null;
  }

}
