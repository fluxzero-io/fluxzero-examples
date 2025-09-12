package com.example.app.authentication

enum class Role(vararg val assumedRoles: Role) {
    ADMIN,
    OWNER(ADMIN);

    fun matches(userRole: Role?): Boolean {
        if (userRole == null) {
            return false
        }
        if (this == userRole) {
            return true
        }
        for (assumedRole in userRole.assumedRoles) {
            if (matches(assumedRole)) {
                return true
            }
        }
        return false
    }
}
