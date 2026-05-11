@RegisterType
@ApiDoc
@ApiDocInfo(
        title = "Game Rental API",
        version = "1.0",
        description = """
                API for managing a game rental service.

                Manage the game catalog, rentals, catalog statistics, and application users.
                """,
        serveOpenApi = true,
        serveApiReference = true)
package com.example.app;

import io.fluxzero.common.serialization.RegisterType;
import io.fluxzero.sdk.web.ApiDoc;
import io.fluxzero.sdk.web.ApiDocInfo;
