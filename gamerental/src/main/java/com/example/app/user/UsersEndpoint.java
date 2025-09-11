package com.example.app.user;

import com.example.app.authentication.Role;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.web.HandleGet;
import io.fluxzero.sdk.web.HandlePost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsersEndpoint {
    @HandlePost("/users")
    UserId createUser(UserDetails details) {
        var userId = new UserId();
        Fluxzero.sendCommandAndWait(new CreateUser(userId, details, Role.manager));
        return userId;
    }

    @HandleGet("/users")
    List<UserProfile> getUsers() {
        return Fluxzero.queryAndWait(new GetUsers());
    }

}
