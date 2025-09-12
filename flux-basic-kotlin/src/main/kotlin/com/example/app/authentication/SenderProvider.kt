package com.example.app.authentication

import com.example.app.user.api.UserId
import com.example.app.user.api.model.UserProfile
import io.fluxzero.common.MessageType
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.common.HasMessage
import io.fluxzero.sdk.common.serialization.DeserializingMessage
import io.fluxzero.sdk.tracking.handling.authentication.AbstractUserProvider
import io.fluxzero.sdk.tracking.handling.authentication.User

class SenderProvider : AbstractUserProvider(Sender::class.java) {

    override fun fromMessage(message: HasMessage): User {
        if (message is DeserializingMessage && message.messageType == MessageType.WEBREQUEST) {
            //for demo purposes, let's assume that everyone sending web requests is admin. Don't use this in the real world! :P
            return Sender.system
        }
        return super.fromMessage(message)
    }

    override fun getUserById(userId: Any): User {
        val userProfile = Fluxzero.loadAggregate(userId, UserProfile::class.java).get()
        if (userProfile == null) {
            //return a new unprivileged user if the user doesn't exist yet
            val uId = if (userId is UserId) userId else UserId(userId.toString())
            return Sender(userId = uId, userRole = null)
        }
        return Sender(userId = userProfile.userId, userRole = userProfile.role)
    }

    override fun getSystemUser(): User {
        return Sender.system
    }
}