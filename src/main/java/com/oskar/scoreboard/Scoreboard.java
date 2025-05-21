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

    public void updateScore(String home, String away, int homeScore, int awayScore) {
        Game game = findGame(home, away);

        if (game.getEndTime() != null) {
            throw new IllegalStateException("Cannot update score. Game between " + home + " and " + away + " has already finished.");
        }

        game.updateScore(homeScore, awayScore);
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

    private Game findGame(String home, String away) {
        return games.stream()
                .filter(g -> g.getHomeTeam().equals(home) && g.getAwayTeam().equals(away))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game between " + home + " and " + away + " not found."));
    }
}
