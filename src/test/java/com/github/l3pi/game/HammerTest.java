package com.github.l3pi.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HammerTest {

    private Hammer hammer0;
    private Hammer hammer1;
    private Hammer hammer2;

    @Before
    public void setUp() {
        hammer0 = new Hammer();

        hammer1 = new Hammer();
        hammer1.addCard();
        hammer1.addCard();

        hammer1.addGold(5);

        hammer2 = new Hammer();
        hammer2.addCard();
        hammer2.addCard();

        hammer2.addGold(37);
    }

    @Test
    public void getCardCount() {
        assertEquals(0, hammer0.getCardCount());
        assertEquals(2, hammer1.getCardCount());
        assertEquals(1, hammer2.getCardCount());
    }

    @Test
    public void getGold() {
        assertEquals(0, hammer0.getGold());
        assertEquals(5, hammer1.getGold());
        assertEquals(7, hammer2.getGold());
    }

    @Test
    public void availableSpace() {
        assertEquals(0, hammer0.getAvailableSpace());
        assertEquals(55, hammer1.getAvailableSpace());
        assertEquals(23, hammer2.getAvailableSpace());
    }

    @Test
    public void addGold() {
        assertEquals(0, hammer1.addGold(2));
        assertEquals(7, hammer1.getGold());
        assertEquals(25, hammer1.addGold(23));
        assertEquals(0, hammer1.getGold());
        assertEquals(1, hammer1.getCardCount());

        assertEquals(10, hammer2.addGold(8));
        assertEquals(15, hammer2.getGold());
        assertEquals(15, hammer2.addGold(15));
        assertEquals(0, hammer2.getGold());
        assertEquals(0, hammer2.getCardCount());
    }

    @Test
    public void addCard() {
        hammer0.addCard();

        assertEquals(1, hammer0.getCardCount());
        assertEquals(2, hammer1.getCardCount());
        assertEquals(1, hammer2.getCardCount());
    }
}
