package com.example.flux.tasks

import io.fluxzero.sdk.modeling.Id

data class TaskId(
    val taskId: String,
) : Id<Task>(taskId)
