package com.example.app.user;

import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.tracking.TrackSelf;
import io.fluxzero.sdk.tracking.handling.HandleCommand;
import jakarta.validation.constraints.NotNull;

@TrackSelf
public interface UserCommand {
    @NotNull UserId userId();

    @HandleCommand
    default void handle() {
        FluxCapacitor.loadAggregate(userId()).assertAndApply(this);
    }
}
