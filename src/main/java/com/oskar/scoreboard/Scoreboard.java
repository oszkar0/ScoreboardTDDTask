package com.oskar.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
    private final List<Game> games = new ArrayList<>();

    public void startGame(String home, String away) {
        if (isGameAlreadyRunning(home, away)) {
            throw new IllegalArgumentException("Game between " + home + " and " + away + " is already running");
        }

        Game game = Game.startGame(home, away);
        games.add(game);
    }

    public List<Game> getSummary() {
        return Collections.unmodifiableList(games);
    }

    private boolean isGameAlreadyRunning(String home, String away) {
        return games.stream()
                .anyMatch(g ->
                        g.getHomeTeam().equals(home) &&
                                g.getAwayTeam().equals(away) &&
                                g.getEndTime() == null
                );
    }
}
