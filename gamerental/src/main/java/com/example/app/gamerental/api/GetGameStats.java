package com.example.app.gamerental.api;

import com.example.app.gamerental.api.common.Game;
import io.fluxzero.common.api.search.FacetStats;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.tracking.handling.HandleQuery;
import io.fluxzero.sdk.tracking.handling.Request;

import java.util.List;

public record GetGameStats(String name) implements Request<List<FacetStats>> {
    @HandleQuery
    List<FacetStats> handle() {
        return Fluxzero.search(Game.class).facetStats().stream()
                .filter(facet -> facet.getName().equals(name)).toList();
    }
}
