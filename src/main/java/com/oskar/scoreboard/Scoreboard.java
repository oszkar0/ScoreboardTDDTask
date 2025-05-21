package com.oskar.scoreboard;

import java.util.ArrayList;
import java.util.Comparator;
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
        game.updateScore(homeScore, awayScore);
    }

    public void finishGame(String home, String away) {
        Game game = findGame(home, away);

        if (game.getEndTime() != null) {
            throw new IllegalStateException("Cannot finish game. Game between " + home + " and " + away + " is already finished.");
        }

        game.finish();
    }

    public List<Game> getSummary() {
        return games.stream()
                .sorted(Comparator
                        .comparingInt(Game::getTotalScore)
                        .thenComparing(Game::getStartTime)
                        .reversed()
                )
                .toList();
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
                .filter(g -> g.getHomeTeam().equals(home) && g.getAwayTeam().equals(away) && g.getEndTime() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ongoing game between " + home + " and " + away + " not found."));
    }
}
