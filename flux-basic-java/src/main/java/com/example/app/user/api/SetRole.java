package com.example.app.user.api;

import com.example.app.authentication.RequiresRole;
import com.example.app.authentication.Role;
import com.example.app.user.api.model.UserProfile;
import io.fluxzero.sdk.modeling.AssertLegal;
import io.fluxzero.sdk.persisting.eventsourcing.Apply;
import io.fluxzero.sdk.tracking.handling.IllegalCommandException;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

@RequiresRole(Role.admin)
public record SetRole(@NotNull UserId userId, Role role) implements UserCommand {
    @AssertLegal
    void assertExists(@Nullable UserProfile profile) {
        if (profile == null) {
            throw new IllegalCommandException("User not found");
        }
    }

    @Apply
    UserProfile apply(UserProfile profile) {
        return profile.toBuilder().role(role).build();
    }
}
