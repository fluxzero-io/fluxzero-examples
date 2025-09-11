package com.example.app.gamerental.api;

import com.example.app.gamerental.api.common.Game;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.tracking.handling.HandleQuery;
import io.fluxzero.sdk.tracking.handling.Request;

import java.util.List;

public record GetGameCatalog() implements Request<List<Game>> {
    @HandleQuery
    List<Game> handle() {
        return Fluxzero.search(Game.class).fetch(100);
    }
}
