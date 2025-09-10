package com.example.app.user;

import com.example.app.authentication.Role;
import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.web.HandleGet;
import io.fluxzero.sdk.web.HandlePost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersEndpoint {
    @HandlePost("/users")
    UserId createUser(UserDetails details) {
        var userId = new UserId();
        FluxCapacitor.sendCommandAndWait(new CreateUser(userId, details, Role.manager));
        return userId;
    }

    @HandleGet("/users")
    List<UserProfile> getUsers() {
        return FluxCapacitor.queryAndWait(new GetUsers());
    }

}
