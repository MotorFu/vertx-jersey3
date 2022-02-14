package com.github.vertx.hk2.loader;

import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import org.glassfish.jersey.internal.inject.InjectionManager;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

/**
 * @author fufengming
 * @email ffu@leapcloud.cn
 * @since 2022/2/11 18:43
 */
public class HK2VerticleFactory implements VerticleFactory {
  public static final String PREFIX = "java-hk2";

  private InjectionManager injectionManager;

  @Override
  public String prefix() {
    return PREFIX;
  }

  /**
   * Close the factory. The implementation must release all resources.
   */
  @Override
  public void close() {
    if (injectionManager != null) {
      injectionManager.shutdown();
      injectionManager = null;
    }
  }

  /**
   * Returns the current parent locator
   *
   * @return
   */
  public InjectionManager getInjectionManager() {
    if (injectionManager == null) {
      injectionManager = createInjectionManager();
    }
    return injectionManager;
  }

  /**
   * Sets the parent locator
   *
   * @param injectionManager
   * @return
   */
  public HK2VerticleFactory setInjectionManager(InjectionManager injectionManager) {
    this.injectionManager = injectionManager;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createVerticle(String verticleName, ClassLoader classLoader, Promise<Callable<Verticle>> promise) {
    verticleName = VerticleFactory.removePrefix(verticleName);

    Constructor<Verticle> ctor;
    try {
      // Use the provided class loader to create an instance of HK2VerticleLoader.  This is necessary when working with vert.x IsolatingClassLoader
      @SuppressWarnings("unchecked")
      Class<Verticle> loader = (Class<Verticle>) classLoader.loadClass(HK2VerticleLoader.class.getName());
      ctor = loader.getConstructor(String.class, ClassLoader.class, InjectionManager.class);
    } catch (ClassNotFoundException | NoSuchMethodException e) {
      promise.fail(e);
      return;
    }

    String finalVerticleName = verticleName;
    promise.complete(() -> ctor.newInstance(finalVerticleName, classLoader, getInjectionManager()));

  }

  protected InjectionManager createInjectionManager() {
    return null;
  }

  /**
   * Returns the verticle identifier with the hk2 prefix for deployment
   *
   * @param clazz
   * @return
   */
  public static String getIdentifier(Class<? extends Verticle> clazz) {
    return getIdentifier(clazz.getName());
  }

  /**
   * Returns the verticle identifier with the hk2 prefix for deployment
   *
   * @param clazz
   * @return
   */
  public static String getIdentifier(String clazz) {
    return PREFIX + ":" + clazz;
  }
}
