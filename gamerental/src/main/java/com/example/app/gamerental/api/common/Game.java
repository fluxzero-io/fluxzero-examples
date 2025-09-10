package com.example.app.gamerental.api.common;

import com.example.app.authentication.Sender;
import io.fluxzero.common.search.Facet;
import io.fluxzero.sdk.modeling.Aggregate;
import io.fluxzero.sdk.modeling.EntityId;
import lombok.Builder;

@Aggregate(searchable = true, timestampPath = "details/releaseDate")
@Builder(toBuilder = true)
public record Game(@EntityId GameId gameId, GameDetails details, @Facet int stock, Sender renter) {
}
