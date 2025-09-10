package com.example.app.gamerental;

import com.example.app.gamerental.api.GetGameCatalog;
import com.example.app.gamerental.api.RegisterGame;
import com.example.app.gamerental.api.common.Game;
import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.tracking.handling.HandleNotification;
import io.fluxzero.sdk.web.HandleSocketOpen;
import io.fluxzero.sdk.web.Path;
import io.fluxzero.sdk.web.SocketEndpoint;
import io.fluxzero.sdk.web.SocketSession;

import java.util.List;

@SocketEndpoint
@Path("/games")
public record GameSocket(SocketSession session) {
    @HandleSocketOpen
    static GameSocket onOpen(SocketSession session) {
        session.sendMessage(FluxCapacitor.queryAndWait(new GetGameCatalog()));
        return new GameSocket(session);
    }

    @HandleNotification(allowedClasses = RegisterGame.class)
    void on(Game game) {
        session.sendMessage(List.of(game));
    }
}
