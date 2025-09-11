package com.example.app;

import io.fluxzero.sdk.web.HandleGet;
import io.fluxzero.sdk.web.Path;
import io.fluxzero.sdk.web.PathParam;
import io.fluxzero.sdk.web.ServeStatic;
import org.springframework.stereotype.Component;

@Component
@Path("/example")
@ServeStatic(value = "/static")
public class ExampleApi {

    @HandleGet("/{number}")
    Integer getNumber(@PathParam Integer number) {
        return number;
    }

}
