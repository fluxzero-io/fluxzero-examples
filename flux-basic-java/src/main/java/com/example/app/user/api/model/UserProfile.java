package com.example.app.user.api.model;

import com.example.app.authentication.Role;
import com.example.app.user.api.UserId;
import io.fluxzero.sdk.modeling.Aggregate;
import io.fluxzero.sdk.modeling.EventPublication;
import lombok.Builder;

@Aggregate(searchable = true, eventPublication = EventPublication.IF_MODIFIED)
@Builder(toBuilder = true)
public record UserProfile(UserId userId, UserDetails details, Role role) {
}
