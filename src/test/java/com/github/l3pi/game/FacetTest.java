package com.github.l3pi.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class FacetTest {

    private Facet facet1;
    private Facet facet2;

    private Operation op1;
    private Operation op2;

    @Before
    public void setUp(){
        op1 = (game, player) -> {
        };
        op2 = (game, player) -> {
        };

        facet1 = new Facet("facet1", 4, op1);
        facet2 = new Facet("facet2", op2);
    }

    @Test
    public void getOperation() {
        assertEquals("facet1", facet1.getName());
        assertEquals("facet2", facet2.getName());
    }

    @Test
    public void getName() {
        assertEquals(op1, facet1.getOperation());
        assertNotEquals(op2, facet1.getOperation());
        assertEquals(op2, facet2.getOperation());
        assertNotEquals(op1, facet2.getOperation());
    }

    @Test
    public void getGoldCost() {
        assertEquals(4, facet1.getGoldCost());
        assertEquals(0, facet2.getGoldCost());
    }
}
