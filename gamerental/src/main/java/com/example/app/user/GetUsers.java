package com.example.app.user;

import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.tracking.handling.HandleQuery;
import io.fluxzero.sdk.tracking.handling.Request;

import java.util.List;

public record GetUsers() implements Request<List<UserProfile>> {
    @HandleQuery
    List<UserProfile> handle() {
        return Fluxzero.search(UserProfile.class).fetchAll();
    }
}
