package com.oskar.scoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void shouldCreateGameWithInitialScoreZeroZero() {
        Game game = new Game("Poland", "Germany");

        assertEquals("Poland", game.getHomeTeam());
        assertEquals("Germany", game.getAwayTeam());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
    }

    @Test
    void shouldUpdateScoreCorrectly() {
        Game game = new Game("France", "Brazil");
        game.updateScore(2, 3);

        assertEquals(2, game.getHomeScore());
        assertEquals(3, game.getAwayScore());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNegativeResultPassed() {
        Game game = new Game("Portugal", "USA");

        assertThrows(IllegalArgumentException.class, () -> {
            game.updateScore(-1, 3);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.updateScore(2, -5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            game.updateScore(-2, -1);
        });
    }

    @Test
    void shouldReturnTotalScore() {
        Game game = new Game("Italy", "Spain");
        game.updateScore(4, 1);

        assertEquals(5, game.getTotalScore());
    }
}