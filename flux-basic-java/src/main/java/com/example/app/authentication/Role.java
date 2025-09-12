package com.example.app.authentication;

import lombok.Getter;

@Getter
public enum Role {
    admin,
    owner(admin);

    private final Role[] assumedRoles;

    Role(Role... assumedRoles) {
        this.assumedRoles = assumedRoles;
    }

    public boolean matches(Role userRole) {
        if (userRole == null) {
            return false;
        }
        if (this == userRole) {
            return true;
        }
        for (Role assumedRole : userRole.getAssumedRoles()) {
            if (matches(assumedRole)) {
                return true;
            }
        }
        return false;
    }
}
