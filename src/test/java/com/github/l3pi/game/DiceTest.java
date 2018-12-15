package com.github.l3pi.game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiceTest {

    private Dice dice;
    private ArrayList<Facet> facets;

    @Before
    public void setUp(){

        facets = new ArrayList<Facet>() {{
            add(new Facet("facet1", (game, player) -> {
            }));
            add(new Facet("facet2", (game, player) -> {
            }));
            add(new Facet("facet3", (game, player) -> {
            }));
            add(new Facet("facet4", (game, player) -> {
            }));
            add(new Facet("facet5", (game, player) -> {
            }));
            add(new Facet("facet6", (game, player) -> {
            }));
        }};

        dice = new Dice(facets);
    }

    @Test
    public void throwDice() {
        Facet result = dice.throwDice();
        assertEquals(dice.getFaceUp(), result);
    }

    @Test
    public void getFaceUp() {
        assertEquals(dice.getFacet(0), dice.getFaceUp());
    }

    @Test
    public void forgeFacet() {
        Facet facet = new Facet("facet7", (game, player) -> {});
        Facet oldFacet = dice.getFacet(5);

        dice.forgeFacet(facet, 5);

        assertEquals(facet, dice.getFaceUp());
        assertFalse(dice.getFacets().stream().anyMatch(facet1 -> facet1.equals(oldFacet)));
    }

    @Test
    public void getFacet() {
        for (int i = 0; i < facets.size(); i++) {
            assertEquals(facets.get(i), dice.getFacet(i));
        }

        boolean th = false;

        try {
            dice.getFacet(7);
        } catch (IndexOutOfBoundsException e) {
            th = true;
        }

        assertTrue(th);
    }

    @Test
    public void getFacets() {
        assertArrayEquals(facets.toArray(), dice.getFacets().toArray());
    }
}
