package com.example.app.user;

import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.tracking.TrackSelf;
import io.fluxzero.sdk.tracking.handling.HandleCommand;
import jakarta.validation.constraints.NotNull;

@TrackSelf
public interface UserCommand {
    @NotNull UserId userId();

    @HandleCommand
    default void handle() {
        Fluxzero.loadAggregate(userId()).assertAndApply(this);
    }
}
