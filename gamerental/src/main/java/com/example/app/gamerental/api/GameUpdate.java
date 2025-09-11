package com.example.app.gamerental.api;

import com.example.app.gamerental.api.common.Game;
import com.example.app.gamerental.api.common.GameErrors;
import com.example.app.gamerental.api.common.GameId;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.modeling.AssertLegal;
import io.fluxzero.sdk.publishing.routing.RoutingKey;
import io.fluxzero.sdk.tracking.TrackSelf;
import io.fluxzero.sdk.tracking.handling.HandleCommand;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;

@TrackSelf
public interface GameUpdate {
    @NotNull
    @RoutingKey
    GameId gameId();

    @AssertLegal
    default void assertExistence(@Nullable Game current) {
        if (current == null) {
            throw GameErrors.notFound;
        }
    }

    @HandleCommand
    default Object handle() {
        Fluxzero.loadAggregate(gameId()).assertAndApply(this);
        return null;
    }
}
