package com.example.app.user;

import com.example.app.authentication.Role;
import com.example.app.authentication.Sender;
import io.fluxzero.sdk.common.serialization.FilterContent;
import io.fluxzero.sdk.modeling.Aggregate;
import lombok.Builder;

@Aggregate(searchable = true)
@Builder(toBuilder = true)
public record UserProfile(UserId userId, UserDetails details, Role role) {
    @FilterContent
    UserProfile filter(Sender sender) {
        return sender.isAuthorizedFor(userId) ? this : null;
    }
}
