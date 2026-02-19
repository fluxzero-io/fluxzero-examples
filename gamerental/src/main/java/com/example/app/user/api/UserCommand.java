package com.example.app.user.api;

import com.example.app.authentication.Sender;
import com.example.app.user.api.model.UserId;
import com.example.app.user.api.model.UserProfile;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.modeling.AssertLegal;
import io.fluxzero.sdk.tracking.TrackSelf;
import io.fluxzero.sdk.tracking.handling.HandleCommand;
import jakarta.validation.constraints.NotNull;

@TrackSelf
public interface UserCommand {
    @NotNull
    UserId userId();


    @AssertLegal
    default void assertAuthorized(UserProfile user, Sender sender) {
        if (!sender.isAdmin() && !user.userId().equals(sender.userId())) {
            throw UserErrors.unauthorized;
        }
    }

    @HandleCommand
    default void handle() {
        Fluxzero.loadAggregate(userId()).assertAndApply(this);
    }
}
