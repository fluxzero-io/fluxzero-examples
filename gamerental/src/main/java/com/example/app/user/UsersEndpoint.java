package com.example.app.user;

import com.example.app.authentication.Role;
import com.example.app.user.api.CreateUser;
import com.example.app.user.api.GetUsers;
import com.example.app.user.api.model.UserDetails;
import com.example.app.user.api.model.UserId;
import com.example.app.user.api.model.UserProfile;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.web.ApiDoc;
import io.fluxzero.sdk.web.HandleGet;
import io.fluxzero.sdk.web.HandlePost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ApiDoc(tags = "Users", description = "User management endpoints.")
public class UsersEndpoint {
    @HandlePost("/users")
    @ApiDoc(
            summary = "Create manager user",
            operationId = "createUser",
            description = "Creates a user profile with manager permissions.")
    UserId createUser(@ApiDoc(description = "User details for the new manager.") UserDetails details) {
        var userId = Fluxzero.generateId(UserId.class);
        Fluxzero.sendCommandAndWait(new CreateUser(userId, details, Role.manager));
        return userId;
    }

    @HandleGet("/users")
    @ApiDoc(
            summary = "List users",
            operationId = "getUsers",
            description = "Returns the user profiles visible to the current sender.")
    List<UserProfile> getUsers() {
        return Fluxzero.queryAndWait(new GetUsers());
    }

}
