package com.oskar.scoreboard.it;

import com.oskar.scoreboard.Game;
import com.oskar.scoreboard.Scoreboard;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardIT {
    @Test
    void testTaskExample() throws InterruptedException {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startGame("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        Thread.sleep(100);

        scoreboard.startGame("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        Thread.sleep(100);


        scoreboard.startGame("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);
        Thread.sleep(100);

        scoreboard.startGame("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        Thread.sleep(100);

        scoreboard.startGame("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Game> summary = scoreboard.getSummary();

        assertEquals("Uruguay 6 - Italy 6", summary.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", summary.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", summary.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", summary.get(3).toString());
        assertEquals("Germany 2 - France 2", summary.get(4).toString());
    }
}
