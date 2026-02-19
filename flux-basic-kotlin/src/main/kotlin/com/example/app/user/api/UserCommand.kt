package com.example.app.user.api

import com.example.app.authentication.Sender
import com.example.app.user.api.model.UserProfile
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.tracking.TrackSelf
import io.fluxzero.sdk.tracking.handling.HandleCommand

@TrackSelf
interface UserCommand {
    val userId: UserId

    @AssertLegal
    fun assertAuthorized(user: UserProfile?, sender: Sender) {
        if (!sender.isAdmin() && user?.userId != sender.userId) {
            throw UserErrors.unauthorized;
        }
    }

    @HandleCommand
    fun handle() {
        Fluxzero.loadAggregate(userId).assertAndApply(this)
    }
}
