package com.example.app.gamerental.api;

import com.example.app.gamerental.api.common.Game;
import com.example.app.gamerental.api.common.GameId;
import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.tracking.handling.HandleQuery;
import io.fluxzero.sdk.tracking.handling.Request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GetGame(@NotNull GameId gameId) implements Request<Game> {
    public GetGame(@NotBlank String gameId) {
        this(new GameId(gameId));
    }

    @HandleQuery
    Game handle() {
        return FluxCapacitor.getDocument(gameId, Game.class).orElse(null);
    }
}
