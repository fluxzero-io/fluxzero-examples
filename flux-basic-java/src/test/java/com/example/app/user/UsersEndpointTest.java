package com.example.app.user;

import com.example.app.user.api.CreateUser;
import com.example.app.user.api.UserId;
import com.example.app.user.api.model.UserProfile;
import io.fluxzero.sdk.test.TestFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

@Nested
class UsersEndpointTest {

    final TestFixture testFixture = TestFixture.create(new UsersEndpoint());

    @Test
    void createUser() {
        testFixture.whenPost("/api/users", "/user/create-user-request.json")
                .expectResult(UserId.class)
                .expectEvents(CreateUser.class);
    }

    @Test
    void getUsers() {
        testFixture.givenPost("/api/users", "/user/create-user-request.json")
                .whenGet("/api/users")
                .<List<UserProfile>>expectResult(r -> r.size() == 1);
    }
}
