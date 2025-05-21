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
}