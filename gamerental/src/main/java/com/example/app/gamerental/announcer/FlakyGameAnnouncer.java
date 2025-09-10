package com.example.app.gamerental.announcer;

import com.example.app.gamerental.api.RegisterGame;
import io.fluxzero.sdk.FluxCapacitor;
import io.fluxzero.sdk.tracking.Consumer;
import io.fluxzero.sdk.tracking.handling.HandleEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Consumer(name = "misterFlaky")
public class FlakyGameAnnouncer {
    private final AtomicInteger counter = new AtomicInteger();

    @HandleEvent
    void on(RegisterGame event) {
        if (counter.getAndIncrement() % 2 == 0) {
            FluxCapacitor.publishEvent(new GameAnnouncement(event.gameId(), "Flaky game announcement"));
        } else {
            throw new RuntimeException("Bad announcer!");
        }
    }

}
