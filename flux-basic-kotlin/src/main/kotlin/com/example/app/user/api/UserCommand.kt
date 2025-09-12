package com.example.app.user.api

import com.example.app.authentication.Sender
import com.example.app.user.api.model.UserProfile
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.tracking.TrackSelf
import io.fluxzero.sdk.tracking.handling.HandleCommand
import io.fluxzero.sdk.tracking.handling.authentication.UnauthorizedException

@TrackSelf
interface UserCommand {
    val userId: UserId

    @AssertLegal
    fun assertAuthorized(user: UserProfile?, sender: Sender) {
        if (!sender.isAdmin() && user?.userId != sender.userId) {
            throw UnauthorizedException("Not authorized to perform this operation")
        }
    }

    @HandleCommand
    fun handle() {
        Fluxzero.loadAggregate(userId).assertAndApply(this)
    }
}
