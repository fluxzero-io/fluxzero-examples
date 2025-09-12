package com.example.app.user.api

import com.example.app.authentication.RequiresRole
import com.example.app.authentication.Role
import com.example.app.user.api.model.UserDetails
import com.example.app.user.api.model.UserProfile
import io.fluxzero.sdk.modeling.AssertLegal
import io.fluxzero.sdk.persisting.eventsourcing.Apply
import io.fluxzero.sdk.tracking.handling.IllegalCommandException
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

@RequiresRole(Role.admin)
data class CreateUser(
    @field:NotNull override val userId: UserId,
    @field:NotNull @field:Valid val details: UserDetails,
    val role: Role?
) : UserCommand {
    
    @AssertLegal
    fun assertNewUser(profile: UserProfile?) {
        if (profile != null) {
            throw IllegalCommandException("User already exists")
        }
    }

    @Apply
    fun apply(): UserProfile {
        return UserProfile.builder()
            .userId(userId)
            .details(details)
            .role(role)
            .build()
    }
}