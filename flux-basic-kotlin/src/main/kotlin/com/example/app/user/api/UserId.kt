package com.example.app.user.api

import com.example.app.user.api.model.UserProfile
import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.modeling.Id

class UserId : Id<UserProfile> {
    constructor() : this(Fluxzero.generateId())
    
    constructor(id: String) : super(id)
}
