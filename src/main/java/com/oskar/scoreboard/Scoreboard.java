package com.oskar.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
    private final List<Game> games = new ArrayList<>();

    public void startGame(String home, String away) {
        Game game = Game.startGame(home, away);
        games.add(game);
    }

    public List<Game> getSummary() {
        return Collections.unmodifiableList(games);
    }
}
