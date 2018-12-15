package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class CardTest {

    private Card card1;
    private Card card2;
    private Card card3;

    private Operation op1;
    private Operation op2;
    private Operation op3;


    @Before
    public void setUp() {
        op1 = (game, player) -> {
        };
        op2 = (game, player) -> {
        };
        op3 = (game, player) -> {
        };

        card1 = new Card(1, ResourceType.LUNAR, CardLocationType.LUNAR1, op1, "card1");
        card2 = new Card(2, ResourceType.SOLAR, CardLocationType.SOLAR1, op2, "card2", true, "");

        ArrayList<ResourceType> ress3 = new ArrayList<ResourceType>() {{
            add(ResourceType.LUNAR);
            add(ResourceType.SOLAR);
        }};

        card3 = new Card(3, ress3, CardLocationType.MIDDLE, op3, "card3", false, "");
    }

    @Test
    public void getName() {
        assertEquals("card1", card1.getName());
        assertEquals("card2", card2.getName());
        assertEquals("card3", card3.getName());

    }

    @Test
    public void getPrice() {
        assertEquals(1, card1.getPrice());
        assertEquals(2, card2.getPrice());
        assertEquals(3, card3.getPrice());

    }

    @Test
    public void getResourceType() {
        assertArrayEquals(new ResourceType[]{ResourceType.LUNAR}, card1.getResourceType().toArray());
        assertArrayEquals(new ResourceType[]{ResourceType.SOLAR}, card2.getResourceType().toArray());
        assertArrayEquals(new ResourceType[]{ResourceType.LUNAR, ResourceType.SOLAR}, card3.getResourceType().toArray());
    }

    @Test
    public void getLocation() {
        assertEquals(CardLocationType.LUNAR1, card1.getLocation());
        assertEquals(CardLocationType.SOLAR1, card2.getLocation());
        assertEquals(CardLocationType.MIDDLE, card3.getLocation());
    }

    @Test
    public void getOperation() {
        assertEquals(op1, card1.getOperation());
        assertNotEquals(op2, card1.getOperation());
        assertNotEquals(op3, card1.getOperation());

        assertNotEquals(op1, card2.getOperation());
        assertEquals(op2, card2.getOperation());
        assertNotEquals(op3, card2.getOperation());

        assertNotEquals(op1, card3.getOperation());
        assertNotEquals(op2, card3.getOperation());
        assertEquals(op3, card3.getOperation());
    }

    @Test
    public void isRecurrent() {
        assertFalse(card1.isRecurrent());
        assertTrue(card2.isRecurrent());
        assertFalse(card3.isRecurrent());
    }

    @Test
    public void isPurchasable() {

        HashMap<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>() {{
            put(ResourceType.LUNAR, 1);
            put(ResourceType.SOLAR, 3);
        }};

        assertTrue(card1.isPurchasable(resources));
        assertTrue(card2.isPurchasable(resources));
        assertFalse(card3.isPurchasable(resources));

        resources = new HashMap<ResourceType, Integer>() {{
            put(ResourceType.LUNAR, 3);
            put(ResourceType.SOLAR, 3);
        }};

        assertTrue(card1.isPurchasable(resources));
        assertTrue(card2.isPurchasable(resources));
        assertTrue(card3.isPurchasable(resources));

        resources = new HashMap<ResourceType, Integer>() {{
            put(ResourceType.LUNAR, 0);
            put(ResourceType.SOLAR, 10);
        }};

        assertFalse(card1.isPurchasable(resources));
        assertTrue(card2.isPurchasable(resources));
        assertFalse(card3.isPurchasable(resources));

    }
}


