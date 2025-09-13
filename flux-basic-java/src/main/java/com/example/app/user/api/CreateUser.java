package com.example.app.user.api;

import com.example.app.authentication.RequiresRole;
import com.example.app.authentication.Role;
import com.example.app.user.api.model.UserDetails;
import com.example.app.user.api.model.UserProfile;
import io.fluxzero.sdk.modeling.AssertLegal;
import io.fluxzero.sdk.persisting.eventsourcing.Apply;
import io.fluxzero.sdk.tracking.handling.IllegalCommandException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RequiresRole(Role.ADMIN)
public record CreateUser(@NotNull UserId userId,
                         @NotNull @Valid UserDetails details,
                         Role role) implements UserCommand {
    @AssertLegal
    void assertNewUser(UserProfile profile) {
        throw new IllegalCommandException("User already exists");
    }

    @Apply
    UserProfile apply() {
        return UserProfile.builder().userId(userId).details(details).role(role).build();
    }
}
