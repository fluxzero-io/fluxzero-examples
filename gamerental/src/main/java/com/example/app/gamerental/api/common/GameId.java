package com.example.app.gamerental.api.common;

import io.fluxzero.sdk.modeling.Id;

public class GameId extends Id<Game> {
    public GameId(String id) {
        super(id);
    }
}
