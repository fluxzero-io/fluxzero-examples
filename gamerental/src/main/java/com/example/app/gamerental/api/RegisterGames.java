package com.example.app.gamerental.api;

import com.example.app.authentication.RequiresRole;
import com.example.app.authentication.Role;
import io.fluxzero.sdk.tracking.handling.HandleCommand;
import jakarta.validation.Valid;

import java.util.List;

@RequiresRole(Role.manager)
public record RegisterGames(List<@Valid RegisterGame> games) {
    @HandleCommand
    void handle() {
        games.forEach(RegisterGame::handle);
    }
}
