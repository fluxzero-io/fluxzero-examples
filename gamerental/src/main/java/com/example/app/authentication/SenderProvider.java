package com.example.app.authentication;

import com.example.app.user.api.model.UserProfile;
import io.fluxzero.common.MessageType;
import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.common.HasMessage;
import io.fluxzero.sdk.common.serialization.DeserializingMessage;
import io.fluxzero.sdk.tracking.handling.authentication.AbstractUserProvider;
import io.fluxzero.sdk.tracking.handling.authentication.User;

public class SenderProvider extends AbstractUserProvider {

    public SenderProvider() {
        super(Sender.class);
    }

    @Override
    public User fromMessage(HasMessage message) {
        if (message instanceof DeserializingMessage dm && dm.getMessageType() == MessageType.WEBREQUEST) {
            //for demo purposes, let's assume that everyone sending web requests is admin. Don't use this in the real world! :P
            return Sender.system;
        }
        return super.fromMessage(message);
    }

    @Override
    public User getUserById(Object userId) {
        UserProfile userProfile = Fluxzero.loadAggregate(userId, UserProfile.class).get();
        return userProfile == null ? null : Sender.builder().userId(userProfile.userId())
                .userRole(userProfile.role()).build();
    }

    @Override
    public User getSystemUser() {
        return Sender.system;
    }
}
