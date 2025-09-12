package com.example.app.user;

import com.example.app.user.api.CreateUser;
import com.example.app.user.api.GetUsers;
import com.example.app.user.api.UserId;
import com.example.app.user.api.model.UserProfile;
import io.fluxzero.sdk.test.TestFixture;
import io.fluxzero.sdk.tracking.handling.authentication.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserTest {

    final TestFixture testFixture = TestFixture.create();

    @Test
    void createViewer() {
        testFixture.whenCommand("/user/create-user.json")
                .expectEvents("/user/create-user.json");
    }


    @Test
    void createUser() {
        testFixture
                .whenCommand("/user/create-user.json")
                .expectEvents("/user/create-user.json");
    }

    @Test
    void createUserNotAllowedForNonAdmin() {
        testFixture.whenCommandByUser("viewer", "/user/create-admin.json")
                .expectExceptionalResult(UnauthorizedException.class);
    }

    @Test
    void setRole() {
        testFixture
                .givenCommands("/user/create-user.json")
                .whenCommand("/user/make-admin.json")
                .expectEvents("/user/make-admin.json");
    }

    @Test
    void setRoleAsNonAdminNotAllowed() {
        testFixture
                .givenCommands("/user/create-user.json")
                .whenCommandByUser("viewer", "/user/make-admin.json")
                .expectExceptionalResult(UnauthorizedException.class);
    }

    @Test
    void getUsers() {
        testFixture.givenCommands("/user/create-user.json")
                .whenQuery(new GetUsers())
                .expectResult(r -> r.size() == 1);
    }

    @Nested
    class UsersEndpointTests {

        @BeforeEach
        void setUp() {
            testFixture.registerHandlers(new UsersEndpoint());
        }

        @Test
        void createUser() {
            testFixture.whenPost("/users", "/user/create-user-request.json")
                    .expectResult(UserId.class)
                    .expectEvents(CreateUser.class);
        }

        @Test
        void getUsers() {
            testFixture.givenPost("/users", "/user/create-user-request.json")
                    .whenGet("/users")
                    .<List<UserProfile>>expectResult(r -> r.size() == 1);
        }
    }
}