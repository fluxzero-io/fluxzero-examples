package com.example.app.user;

import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.modeling.Id;

public class UserId extends Id<UserProfile> {
    public UserId() {
        this(FluxCapacitor.generateId());
    }

    public UserId(String id) {
        super(id);
    }
}
