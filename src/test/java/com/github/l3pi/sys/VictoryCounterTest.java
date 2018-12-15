package com.github.l3pi.sys;

import com.github.l3pi.game.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class VictoryCounterTest {
    private Player player1;
    private Player player2;
    private Player player3;

    private VictoryCounter counter;

    @Before
    public void initCounter() {
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        player3 = mock(Player.class);

        counter = new VictoryCounter(
            new ArrayList<Player>() {{
                add(player1);
                add(player2);
            }}
        );
    }

    @Test
    public void testThatScoresAreInitializedAt0() {
        assertEquals(0, counter.getVictoryCountFor(player1));
        assertEquals(0, counter.getVictoryCountFor(player2));

        // The counter should ignore nonexistence and just count it as "0" points
        assertEquals(0, counter.getVictoryCountFor(player3));
    }

    @Test
    public void testThatScoreIsIncremented() {

        counter.addVictoryFor(new ArrayList<Player>() {{
            add(player1);
        }});

        assertEquals(1, counter.getVictoryCountFor(player1));
        assertEquals(0, counter.getVictoryCountFor(player2));
    }

    @Test
    public void testThatScoreForNonexistentPlayerIsntIncremented() {
        counter.addVictoryFor(new ArrayList<Player>() {{
            add(player3);
        }});
        assertEquals(0, counter.getVictoryCountFor(player2));
    }

    /**
     * By simple mathematical rule,
     * sum(playerScores) <= playCount * playerCount
     * Here, we test that
     */
    @Test
    public void testThatTotalWinnerCountNeverPassesPlayCount() {
        List<Player> players = new ArrayList<Player>() {{
            add(player1);
            add(player2);
        }};
        counter = new VictoryCounter(players);

        for (int i = 0; i < 100; ++i) {
            // We mark all players as winners
            counter.addVictoryFor(players);
        }

        int totalScore = 0;
        for (Player player : players) {
            totalScore += counter.getVictoryCountFor(player);
        }
        assertTrue(totalScore <= counter.getPlayCount() * players.size());
    }
}
