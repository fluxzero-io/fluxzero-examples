package com.example.app.user;

import com.example.app.authentication.RequiresRole;
import com.example.app.authentication.Role;
import io.fluxzero.sdk.persisting.eventsourcing.Apply;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RequiresRole(Role.admin)
public record CreateUser(@NotNull UserId userId,
                         @NotNull @Valid UserDetails details,
                         Role role) implements UserCommand {
    @Apply
    UserProfile apply() {
        return UserProfile.builder().userId(userId).details(details).role(role).build();
    }
}
