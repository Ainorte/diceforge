package com.github.l3pi.game;

import com.github.l3pi.bot.RandomBot;
import com.github.l3pi.rule.RuleSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void testForGameWithoutPlayers() {
        Game g = new Game(new ArrayList<>(), null);

        assertEquals(0, g.getPlayers().size());
        assertEquals(0, g.getBestPlayer().size());
    }

    @Test
    public void testForGameWithASinglePlayer() {
        Game g = new Game(new ArrayList<Player>() {{
            add(new RandomBot("Meow"));
        }}, null);

        assertEquals(1, g.getPlayers().size());
        assertEquals(1, g.getBestPlayer().size());
    }

    @Test(expected = NullPointerException.class)
    public void testForNoRuleSet() {
        Game g = new Game(new ArrayList<Player>() {{
            add(new RandomBot("Meow"));
        }}, null);

        g.round();
    }

    @Test
    public void testForEmptyRuleSet() {
        Game g = new Game(new ArrayList<Player>() {{
            add(new RandomBot("Meow"));
        }}, new RuleSet(new HashMap<>()));

        g.round();
    }
}
