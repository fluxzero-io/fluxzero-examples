package com.example.app.gamerental;

import com.example.app.gamerental.api.*;
import com.example.app.gamerental.api.common.Game;
import com.example.app.gamerental.api.common.GameDetails;
import com.example.app.gamerental.api.common.GameId;
import io.fluxzero.common.api.search.FacetStats;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.web.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Path("/games")
@ServeStatic(value = "/static")
public class GameApi {

    @HandlePost
    CompletableFuture<GameId> registerGame(GameDetails details) {
        return Fluxzero.sendCommand(new RegisterGame(details));
    }

    @HandleGet("/{gameId}")
    CompletableFuture<Game> getGame(@PathParam GameId gameId) {
        return Fluxzero.query(new GetGame(gameId));
    }

    @HandleGet
    CompletableFuture<List<Game>> getGames(@QueryParam String term) {
        return Fluxzero.query(new FindGames(term));
    }

    @HandleGet("/stats")
    CompletableFuture<List<FacetStats>> getGameStats(@QueryParam String name) {
        return Fluxzero.query(new GetGameStats(name));
    }

    @HandlePost("/{gameId}/rent")
    CompletableFuture<Void> rentGame(@PathParam GameId gameId) {
        return Fluxzero.sendCommand(new RentGame(gameId));
    }

    @HandlePost("/{gameId}/return")
    CompletableFuture<Void> returnGame(@PathParam GameId gameId) {
        return Fluxzero.sendCommand(new ReturnGame(gameId));
    }

}
