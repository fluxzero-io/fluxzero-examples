package com.example.app.user;

import io.fluxzero.sdk.test.TestFixture;
import io.fluxzero.sdk.tracking.handling.IllegalCommandException;
import io.fluxzero.sdk.tracking.handling.authentication.UnauthorizedException;
import org.junit.jupiter.api.Test;

class UserTest {

    final TestFixture testFixture = TestFixture.create();

    @Test
    void createViewer() {
        testFixture.whenCommand("/user/create-customer.json")
                .expectEvents("/user/create-customer.json");
    }


    @Test
    void createUserAllowedAsEditor() {
        testFixture
                .givenCommands("/user/create-admin.json")
                .whenCommandByUser("admin", "/user/create-customer.json")
                .expectEvents("/user/create-customer.json");
    }

    @Test
    void createUserTwiceForbidden() {
        testFixture
                .givenCommands("/user/create-customer.json")
                .whenCommand("/user/create-customer.json")
                .expectExceptionalResult(IllegalCommandException.class);
    }

    @Test
    void createUserNotAllowedAsViewer() {
        testFixture.whenCommandByUser("viewer", "/user/create-admin.json")
                .expectExceptionalResult(UnauthorizedException.class);
    }

    @Test
    void getUsers() {
        testFixture.givenCommands("/user/create-customer.json")
                .whenQuery(new GetUsers())
                .expectResult(r -> r.size() == 1);
    }

}