package com.oskar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    void shouldThrowExceptionWhenGameWithSameTeamsIsAlreadyRunning() {
        String home = "Poland";
        String away = "Germany";
        Game existingGame = mock(Game.class);

        when(existingGame.getHomeTeam()).thenReturn(home);
        when(existingGame.getAwayTeam()).thenReturn(away);
        when(existingGame.getEndTime()).thenReturn(null);


        try (MockedStatic<Game> mockedStatic = mockStatic(Game.class)) {
            mockedStatic.when(() -> Game.startGame(home, away)).thenReturn(existingGame);

            scoreboard.startGame(home, away);
            assertThrows(IllegalArgumentException.class,
                    () -> scoreboard.startGame(home, away));

        }
    }

    @Test
    void shouldUpdateScoreOfRunningGameOnly() {
        String home = "Poland";
        String away = "Germany";

        Game runningGame = mock(Game.class);
        when(runningGame.getHomeTeam()).thenReturn(home);
        when(runningGame.getAwayTeam()).thenReturn(away);
        when(runningGame.getEndTime()).thenReturn(null);


        try (MockedStatic<Game> mocked = mockStatic(Game.class)) {
            mocked.when(() -> Game.startGame(home, away)).thenReturn(runningGame);

            scoreboard.startGame(home, away);
            scoreboard.updateScore(home, away, 2, 1);

            verify(runningGame).updateScore(2, 1);
        }
    }

    @Test
    void shouldNotUpdateScoreOfFinishedGame() {
        String home = "France";
        String away = "Brazil";

        Game finishedGame = mock(Game.class);
        when(finishedGame.getHomeTeam()).thenReturn(home);
        when(finishedGame.getAwayTeam()).thenReturn(away);
        when(finishedGame.getEndTime()).thenReturn(Instant.now());

        try (MockedStatic<Game> mocked = mockStatic(Game.class)) {
            mocked.when(() -> Game.startGame(home, away)).thenReturn(finishedGame);

            scoreboard.startGame(home, away);

            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore(home, away, 3, 3)
            );

            assertEquals("Ongoing game between France and Brazil not found.", ex.getMessage());
            verify(finishedGame, never()).updateScore(3, 3);
        }
    }

    @Test
    void shouldFinishRunningGame() {
        String home = "Argentina";
        String away = "Uruguay";

        Game game = mock(Game.class);
        when(game.getHomeTeam()).thenReturn(home);
        when(game.getAwayTeam()).thenReturn(away);
        when(game.getEndTime()).thenReturn(null);

        try (MockedStatic<Game> mocked = mockStatic(Game.class)) {
            mocked.when(() -> Game.startGame(home, away)).thenReturn(game);

            scoreboard.startGame(home, away);

            scoreboard.finishGame(home, away);

            verify(game).finish();
        }
    }

    @Test
    void shouldReturnGamesSortedByTotalScoreAndStartTime() throws NoSuchFieldException, IllegalAccessException {
        Game game1 = mock(Game.class);
        Game game2 = mock(Game.class);
        Game game3 = mock(Game.class);
        Game game4 = mock(Game.class);

        Instant now = Instant.now();
        Instant earlier = now.minusSeconds(60);

        when(game1.getHomeTeam()).thenReturn("Argentina");
        when(game1.getAwayTeam()).thenReturn("Australia");
        when(game1.getHomeScore()).thenReturn(3);
        when(game1.getAwayScore()).thenReturn(1);
        when(game1.getTotalScore()).thenReturn(4);
        when(game1.getStartTime()).thenReturn(earlier);

        when(game2.getHomeTeam()).thenReturn("Spain");
        when(game2.getAwayTeam()).thenReturn("Brazil");
        when(game2.getHomeScore()).thenReturn(10);
        when(game2.getAwayScore()).thenReturn(2);
        when(game2.getTotalScore()).thenReturn(12);
        when(game2.getStartTime()).thenReturn(earlier);

        when(game3.getHomeTeam()).thenReturn("Germany");
        when(game3.getAwayTeam()).thenReturn("France");
        when(game3.getHomeScore()).thenReturn(2);
        when(game3.getAwayScore()).thenReturn(2);
        when(game3.getTotalScore()).thenReturn(4);
        when(game3.getStartTime()).thenReturn(now);

        when(game4.getHomeTeam()).thenReturn("Uruguay");
        when(game4.getAwayTeam()).thenReturn("Italy");
        when(game4.getHomeScore()).thenReturn(6);
        when(game4.getAwayScore()).thenReturn(6);
        when(game4.getTotalScore()).thenReturn(12);
        when(game4.getStartTime()).thenReturn(now);

        var field = scoreboard.getClass().getDeclaredField("games");
        field.setAccessible(true);
        List<Game> games = (List<Game>) field.get(scoreboard);
        games.addAll(List.of(game1, game2, game3, game4));

        List<Game> summary = scoreboard.getSummary();

        assertEquals(List.of(game4, game2, game3, game1), summary);
    }

}