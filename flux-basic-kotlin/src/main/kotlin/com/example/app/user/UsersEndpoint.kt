package com.example.app.user

import com.example.app.authentication.RequiresRole
import com.example.app.user.api.CreateUser
import com.example.app.user.api.GetUsers
import com.example.app.user.api.UserId
import com.example.app.user.api.model.UserDetails
import com.example.app.user.api.model.UserProfile
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.web.HandleGet
import io.fluxzero.sdk.web.HandlePost
import org.springframework.stereotype.Component

@Component
@RequiresRole
class UsersEndpoint {
    
    @HandlePost("/users")
    fun createUser(details: UserDetails): UserId {
        val userId = UserId()
        Fluxzero.sendCommandAndWait<CreateUser>(CreateUser(userId, details, null))
        return userId
    }

    @HandleGet("/users")
    fun getUsers(): List<UserProfile> {
        return Fluxzero.queryAndWait(GetUsers())
    }
}
