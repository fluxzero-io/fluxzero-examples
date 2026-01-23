package com.example.app.user

import com.example.app.user.api.CreateUser
import com.example.app.user.api.UserId
import com.example.app.user.api.model.UserProfile
import io.fluxzero.sdk.test.TestFixture
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@Nested
class UsersEndpointTests {

    private val testFixture = TestFixture.create(UsersEndpoint())

    @Test
    fun createUser() {
        testFixture.whenPost("/api/users", "/user/create-user-request.json")
            .expectResult(UserId::class.java)
            .expectEvents(CreateUser::class.java)
    }

    @Test
    fun getUsers() {
        testFixture.givenPost("/api/users", "/user/create-user-request.json")
            .whenGet("/api/users")
            .expectResult<List<UserProfile>> { r: List<UserProfile> -> r.size == 1 }
    }
}
