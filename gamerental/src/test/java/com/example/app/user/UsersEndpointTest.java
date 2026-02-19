package com.example.app.user;

import com.example.app.user.api.CreateUser;
import com.example.app.user.api.model.UserId;
import com.example.app.user.api.model.UserProfile;
import io.fluxzero.sdk.test.TestFixture;
import org.junit.jupiter.api.Test;

import java.util.List;

class UsersEndpointTest {

    private final TestFixture testFixture = TestFixture.create(new UsersEndpoint());

    @Test
    void createUser() {
        testFixture.whenPost("/users", "/user/new-user-details.json")
                .expectResult(UserId.class)
                .expectEvents(CreateUser.class);
    }

    @Test
    void getUsers() {
        testFixture.givenPost("/users", "/user/new-user-details.json")
                .whenGet("/users")
                .<List<UserProfile>>expectResult(r -> r.size() == 1);
    }
}
