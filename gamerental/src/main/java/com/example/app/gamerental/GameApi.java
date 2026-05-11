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
@ApiDoc(tags = "Games", description = "Game catalog and rental endpoints.")
public class GameApi {

    @HandlePost
    @ApiDoc(
            summary = "Register game",
            operationId = "registerGame",
            description = "Adds a game to the catalog and returns the generated game id.")
    CompletableFuture<GameId> registerGame(
            @ApiDoc(description = "Game details to add to the catalog.") GameDetails details) {
        return Fluxzero.sendCommand(new RegisterGame(details));
    }

    @HandleGet("/{gameId}")
    @ApiDoc(
            summary = "Get game",
            operationId = "getGame",
            description = "Returns a single game from the catalog.")
    CompletableFuture<Game> getGame(
            @PathParam
            @ApiDoc(description = "Identifier of the game to retrieve.", example = "myGame")
            GameId gameId) {
        return Fluxzero.query(new GetGame(gameId));
    }

    @HandleGet
    @ApiDoc(
            summary = "Find games",
            operationId = "findGames",
            description = "Searches the game catalog. When no term is provided, the catalog is returned.")
    CompletableFuture<List<Game>> getGames(
            @QueryParam
            @ApiDoc(description = "Optional search term matched against game details.",
                    example = "cryptographic mysteries")
            String term) {
        return Fluxzero.query(new FindGames(term));
    }

    @HandleGet("/stats")
    @ApiDoc(
            summary = "Get game statistics",
            operationId = "getGameStats",
            description = "Returns facet statistics for a game catalog field, such as stock or tags.")
    CompletableFuture<List<FacetStats>> getGameStats(
            @QueryParam
            @ApiDoc(description = "Facet field name to aggregate.", example = "stock")
            String name) {
        return Fluxzero.query(new GetGameStats(name));
    }

    @HandlePost("/{gameId}/rent")
    @ApiDoc(
            summary = "Rent game",
            operationId = "rentGame",
            description = "Rents an available game for the current sender.")
    CompletableFuture<Void> rentGame(
            @PathParam
            @ApiDoc(description = "Identifier of the game to rent.", example = "myGame")
            GameId gameId) {
        return Fluxzero.sendCommand(new RentGame(gameId));
    }

    @HandlePost("/{gameId}/return")
    @ApiDoc(
            summary = "Return game",
            operationId = "returnGame",
            description = "Returns a game currently rented by the current sender.")
    CompletableFuture<Void> returnGame(
            @PathParam
            @ApiDoc(description = "Identifier of the game to return.", example = "myGame")
            GameId gameId) {
        return Fluxzero.sendCommand(new ReturnGame(gameId));
    }

}
