package com.oskar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class ScoreboardTest {
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        scoreboard = new Scoreboard();
    }

    @Test
    void shouldStartGameAndAddItToList() {
        String home = "Poland";
        String away = "Germany";
        Game mockGame = mock(Game.class);

        try (MockedStatic<Game> mockedStatic = mockStatic(Game.class)) {
            mockedStatic.when(() -> Game.startGame(home, away)).thenReturn(mockGame);

            scoreboard.startGame(home, away);

            List<Game> summary = scoreboard.getSummary();
            assertEquals(1, summary.size());
            assertEquals(mockGame, summary.get(0));
        }
    }

}