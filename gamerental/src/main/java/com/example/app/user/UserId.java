package com.example.app.user;

import io.fluxzero.sdk.Fluxzero;
import io.fluxzero.sdk.modeling.Id;

public class UserId extends Id<UserProfile> {
    public UserId() {
        this(Fluxzero.generateId());
    }

    public UserId(String id) {
        super(id);
    }
}
