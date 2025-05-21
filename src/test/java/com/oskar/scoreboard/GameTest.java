package com.oskar.scoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void shouldCreateGameWithInitialScoreZeroZero() {
        Game game = Game.startGame("Poland", "Germany");

        assertEquals("Poland", game.getHomeTeam());
        assertEquals("Germany", game.getAwayTeam());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
    }

    @Test
    void shouldUpdateScoreCorrectly() {
        Game game = Game.startGame("France", "Brazil");
        game.updateScore(2, 3);

        assertEquals(2, game.getHomeScore());
        assertEquals(3, game.getAwayScore());
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenNegativeResultPassed() {
        Game game = Game.startGame("Portugal", "USA");

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
        Game game = Game.startGame("Italy", "Spain");
        game.updateScore(4, 1);

        assertEquals(5, game.getTotalScore());
    }

    @Test
    void shouldStartAndEndGame() {
        Game game = Game.startGame("Italy", "Spain");
        assertNotNull(game.getStartTime());
        game.updateScore(4, 1);
        assertNull(game.getEndTime());

        game.finish();
        assertNotNull(game.getEndTime());
    }

    @Test
    void shouldThrowExceptionWhenGameEndedTwice() {
        Game game = Game.startGame("Italy", "Spain");

        game.finish();

        assertThrows(IllegalArgumentException.class, () -> {
            game.finish();
        });
    }
}