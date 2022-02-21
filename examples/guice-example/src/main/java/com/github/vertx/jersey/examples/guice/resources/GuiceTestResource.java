package com.github.vertx.jersey.examples.guice.resources;

import com.github.vertx.jersey.examples.guice.MyDependency;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;


/**
 * Guice JAX-RS endpoint
 */
@Singleton
@Path("guice")
public class GuiceTestResource {

    private final MyDependency myDependency;

    @Inject
    public GuiceTestResource(MyDependency myDependency) {
        this.myDependency = myDependency;
    }

    @GET
    public String doGet() {
        return "Instance of " + myDependency.getClass().getName() + " injected!";
    }

    @GET
    @Path("exception")
    public void doException() {
        throw new InternalServerErrorException("Test exception");
    }

}
