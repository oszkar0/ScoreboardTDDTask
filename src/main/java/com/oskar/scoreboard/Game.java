package com.oskar.scoreboard;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Game {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private Instant startTime;
    private Instant endTime;

    public static Game startGame(String homeTeam, String awayTeam) {
        return new Game(homeTeam, awayTeam);
    }

    private Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = Instant.now();
    }

    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Score must not be a negative number!");
        }

        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getTotalScore() {
        return this.homeScore + this.awayScore;
    }

    public Instant finish() {
        if (this.endTime != null) {
            throw new IllegalArgumentException("Game already finished");
        }

        this.endTime = Instant.now();
        return this.endTime;
    }
}
