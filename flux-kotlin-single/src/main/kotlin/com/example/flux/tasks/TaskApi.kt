package com.example.flux.tasks

import io.fluxzero.sdk.Fluxzero
import io.fluxzero.sdk.web.HandleGet
import io.fluxzero.sdk.web.WebRequest
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class TaskApi {
    @HandleGet("/task/*")
    fun getTask(request: WebRequest): CompletableFuture<Task> {
        // Retrieve the task ID the user has requested
        val taskId = TaskId(request.path.substringAfterLast("/"))
        return Fluxzero.query(GetTask(taskId))
    }
}
