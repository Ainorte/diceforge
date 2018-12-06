package com.github.l3pi.sys;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class VictoryCounterTest {
    private static final String DEFAULT = "meow";

    private VictoryCounter counter;

    @Before
    public void initCounter() {
        counter = new VictoryCounter(
            new ArrayList<String>() {{
                add(DEFAULT);
            }}
        );
    }

    @Test
    public void testThatScoresAreInitializedAt0() {
        assertEquals(0, counter.getVictoryCountFor(DEFAULT));
        // The counter should ignore nonexistence and just count it as "0" points
        assertEquals(0, counter.getVictoryCountFor("nonexistent"));
    }

    @Test
    public void testThatScoreIsIncremented() {
        assertEquals(0, counter.getVictoryCountFor(DEFAULT));
        counter.addVictoryFor(new ArrayList<String>() {{
            add(DEFAULT);
        }});
        assertEquals(1, counter.getVictoryCountFor(DEFAULT));
        // The counter should ignore nonexistence and just count it as "0" points
        assertEquals(0, counter.getVictoryCountFor("nonexistent"));
    }

    @Test
    public void testThatScoreForNonexistentPlayerIsntIncremented() {
        assertEquals(0, counter.getVictoryCountFor("nonexistent"));
        counter.addVictoryFor(new ArrayList<String>() {{
            add("nonexistent");
        }});
        assertEquals(0, counter.getVictoryCountFor("nonexistent"));
    }

    /**
     * By simple mathematical rule,
     * sum(playerScores) <= playCount * playerCount
     * Here, we test that
     */
    @Test
    public void testThatTotalWinnerCountNeverPassesPlayCount() {
        final List<String> players = Arrays.asList("1", "2", "3", "4");
        counter = new VictoryCounter(players);

        for (int i = 0; i < 100; ++i) {
            // We mark all players as winners
            counter.addVictoryFor(players);
        }

        int totalScore = 0;
        for (String player : players) {
            totalScore += counter.getVictoryCountFor(player);
        }
        assertTrue(totalScore <= counter.getPlayCount() * players.size());
    }
}
