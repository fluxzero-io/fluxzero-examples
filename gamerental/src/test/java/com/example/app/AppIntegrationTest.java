package com.example.app;

import com.example.app.gamerental.announcer.GameAnnouncement;
import io.fluxzero.sdk.test.TestFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@SpringBootTest(classes = App.class)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class AppIntegrationTest {

    @Autowired
    TestFixture testFixture;

    @Test
    void errorCorrectionIntegrationTest() {
        testFixture.givenCommands("/game/register-game.json")
                .whenCommand("/game/register-other-game.json")
                .expectError()
                .<GameAnnouncement>expectEvent(a -> a.message().equals("Correct that game announcement"));
    }
}
