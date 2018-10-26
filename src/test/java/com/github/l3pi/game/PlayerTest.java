package com.github.l3pi.game;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player(0, "Test");
    }

    @Test
    public void throwDice() {
        List<Facet> facets = player.throwDice();

        assertEquals(2, facets.size());
    }
}
