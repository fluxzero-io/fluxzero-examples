package com.example.app.gamerental.api;

import com.example.app.authentication.RequiresRole;
import com.example.app.authentication.Role;
import com.example.app.authentication.Sender;
import com.example.app.gamerental.api.common.Game;
import com.example.app.gamerental.api.common.GameErrors;
import com.example.app.gamerental.api.common.GameId;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.modeling.AssertLegal;
import io.fluxzero.sdk.persisting.eventsourcing.Apply;

@RequiresRole(Role.customer)
public record RentGame(GameId gameId) implements GameUpdate {

    @AssertLegal
    void assertStock(Game current) {
        if (current.stock() <= 0) {
            throw GameErrors.outOfStock;
        }
    }

    @AssertLegal
    void assertOut(Game current) {
        if (current.details().releaseDate().isAfter(Fluxzero.currentTime())) {
            throw GameErrors.notOutYet;
        }
    }

    @Apply
    Game apply(Game current, Sender user) {
        return current.toBuilder().stock(current.stock() - 1).renter(user).build();
    }
}
