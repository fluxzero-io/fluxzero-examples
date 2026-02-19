package com.example.app.gamerental.api;

import com.example.app.authentication.RequiresRole;
import com.example.app.authentication.Role;
import com.example.app.gamerental.api.common.Game;
import com.example.app.gamerental.api.common.GameDetails;
import com.example.app.gamerental.api.common.GameId;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.persisting.eventsourcing.Apply;
import io.fluxzero.sdk.tracking.handling.Request;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RequiresRole(Role.manager)
public record RegisterGame(GameId gameId, @NotNull @Valid GameDetails details) implements GameUpdate, Request<GameId> {

    public RegisterGame(@NotNull @Valid GameDetails details) {
        this(Fluxzero.generateId(GameId.class), details);
    }

    @Override
    public GameId handle() {
        GameUpdate.super.handle();
        return gameId;
    }

    @Override
    public void assertExistence(@Nullable Game current) {
        if (current != null) {
            throw GameErrors.alreadyExists;
        }
    }

    @Apply
    Game apply() {
        return Game.builder().gameId(gameId).details(details).stock(1).build();
    }
}
