package com.example.app.gamerental.api;

import com.example.app.gamerental.api.common.Game;
import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.tracking.handling.HandleQuery;
import io.fluxzero.sdk.tracking.handling.Request;
import jakarta.annotation.Nullable;

import java.util.List;

public record FindGames(@Nullable String term) implements Request<List<Game>> {
    @HandleQuery
    List<Game> handle() {
        return FluxCapacitor.search(Game.class).lookAhead(term).fetch(100);
    }
}
