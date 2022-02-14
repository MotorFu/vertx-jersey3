package com.github.vertx.hk2.loader;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.vertx.core.*;
import io.vertx.core.impl.verticle.CompilingClassLoader;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.glassfish.jersey.internal.inject.Binder;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.inject.Injections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fufengming
 * @email ffu@leapcloud.cn
 * @since 2022/2/11 18:42
 */
public class HK2VerticleLoader extends AbstractVerticle {
  private final Logger logger = LoggerFactory.getLogger(HK2VerticleLoader.class);
  private final String verticleName;
  private ClassLoader classLoader;
  private InjectionManager parent;
  private Verticle realVerticle;
  private InjectionManager injectionManager;
  public static final String CONFIG_BOOTSTRAP_BINDER_NAME = "hk2_binder";
  public static final String BOOTSTRAP_BINDER_NAME = "com.github.vertx.jersey.BootstrapBinder";

  public HK2VerticleLoader(String verticleName, ClassLoader classLoader, InjectionManager parent) {
    this.verticleName = verticleName;
    this.classLoader = classLoader;
    this.parent = parent;
  }

  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);

    try {
      this.realVerticle = this.createRealVerticle();
      this.realVerticle.init(vertx, context);
    } catch (Throwable var4) {
      if (var4 instanceof RuntimeException) {
        throw (RuntimeException) var4;
      } else {
        throw new RuntimeException(var4);
      }
    }
  }

  public void start(Promise<Void> startedResult) throws Exception {
    this.realVerticle.start(startedResult);
  }

  public void stop(Promise<Void> stopFuture) {
    this.classLoader = null;
    this.parent = null;
    Promise<Void> future = Promise.promise();
    future.future().onComplete((result) -> {
      this.injectionManager.shutdown();
      this.injectionManager = null;
      if (result.succeeded()) {
        stopFuture.complete();
      } else {
        stopFuture.fail(future.future().cause());
      }

    });

    try {
      if (this.realVerticle != null) {
        this.realVerticle.stop(future);
      } else {
        future.complete();
      }
    } catch (Throwable var4) {
      future.fail(var4);
    }

  }

  public String getVerticleName() {
    return this.verticleName;
  }

  public Verticle createRealVerticle() throws Exception {
    String className = this.verticleName;
    Class<?> clazz;
    if (className.endsWith(".java")) {
      CompilingClassLoader compilingLoader = new CompilingClassLoader(this.classLoader, className);
      className = compilingLoader.resolveMainClassName();
      clazz = compilingLoader.loadClass(className);
    } else {
      clazz = this.classLoader.loadClass(className);
    }

    Verticle verticle = this.createRealVerticle(clazz);
    return verticle;
  }

  private Verticle createRealVerticle(Class<?> clazz) throws Exception {
    JsonObject config = this.config();
    Object field = config.getValue(CONFIG_BOOTSTRAP_BINDER_NAME);
    List<Binder> bootstraps = new ArrayList<>();
    JsonArray bootstrapNames;
    boolean hasBootstrap;
    if (field instanceof JsonArray) {
      hasBootstrap = true;
      bootstrapNames = (JsonArray) field;
    } else {
      hasBootstrap = field != null;
      bootstrapNames = (new JsonArray()).add(field == null ? BOOTSTRAP_BINDER_NAME : field);
    }

    for (int i = 0; i < bootstrapNames.size(); ++i) {
      String bootstrapName = bootstrapNames.getString(i);

      try {
        Class<?> bootstrapClass = this.classLoader.loadClass(bootstrapName);
        Object obj = bootstrapClass.newInstance();
        if (obj instanceof Binder) {
          bootstraps.add((Binder) obj);
        } else {
          this.logger.error("Class " + bootstrapName + " does not implement Binder.");
        }
      } catch (ClassNotFoundException var11) {
        if (hasBootstrap || this.parent == null) {
          this.logger.warn("HK2 bootstrap binder class " + bootstrapName + " was not found.  Are you missing injection bindings?");
        }
      }
    }

    this.injectionManager = Injections.createInjectionManager(parent);
    bootstraps.add(0, new HK2VertxBinder(this.vertx));

    for (Binder binder : bootstraps) {
      this.injectionManager.register(binder);
    }
    return (Verticle) this.injectionManager.createAndInitialize(clazz);
  }
}
