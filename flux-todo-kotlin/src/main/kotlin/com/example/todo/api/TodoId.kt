package com.example.todo.api

import io.fluxzero.sdk.modeling.EntityId
import java.util.UUID

@JvmInline
value class TodoId(
    @EntityId val id: String = UUID.randomUUID().toString()
) {
    override fun toString(): String = id
}