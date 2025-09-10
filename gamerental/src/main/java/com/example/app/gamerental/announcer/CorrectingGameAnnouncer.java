package com.example.app.gamerental.announcer;

import com.example.app.gamerental.api.RegisterGame;
import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.tracking.handling.HandleError;
import io.fluxzero.sdk.tracking.handling.Trigger;
import org.springframework.stereotype.Component;

@Component
public class CorrectingGameAnnouncer {

    @HandleError
    void on(@Trigger(consumer = "misterFlaky") RegisterGame event) {
        FluxCapacitor.publishEvent(new GameAnnouncement(event.gameId(), "Correct that game announcement"));
    }

}
