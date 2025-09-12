package com.example.app.user.api

import com.example.app.authentication.RequiresRole
import com.example.app.authentication.Role
import com.example.app.user.api.model.UserProfile
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import jakarta.validation.constraints.NotNull

@RequiresRole(Role.admin)
data class SetRole(
    @field:NotNull override val userId: UserId,
    val role: Role?
) : UserCommand {
    
    @AssertLegal
    fun assertExists(profile: UserProfile?) {
        if (profile == null) {
            throw IllegalCommandException("User not found")
        }
    }

    @Apply
    fun apply(profile: UserProfile): UserProfile {
        return profile.toBuilder().role(role).build()
    }
}