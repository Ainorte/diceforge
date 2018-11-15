package com.github.l3pi.game;


import com.github.l3pi.bot.RandomBot;
import com.github.l3pi.factory.InventoryFactory;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.FacetType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        inventory = InventoryFactory.getInstance().getInventory(new Game(new ArrayList<>(), null), new RandomBot("test"), 0);
    }

    @Test
    public void throwDice() {
        List<Facet> facets = inventory.throwDice();

        assertEquals(2, facets.size());
    }

    @Test
    public void forge() {
        Facet[] original = inventory.getFaceUp();
        inventory.forge(new Facet(new HashMap<FacetType, Integer>() {{
            put(FacetType.GLORY, 1);
            put(FacetType.SOLAR, 1);
            put(FacetType.LUNAR, 1);
            put(FacetType.GOLD, 1);
        }}), 0, 0);
        Facet[] afterForge = inventory.getFaceUp();

        assertFalse(Arrays.equals(original, afterForge));
    }

    @Test
    public void getFaceUp() {
        Facet[] faceUp = inventory.getFaceUp();

        assertEquals(2, faceUp.length);
    }

    @Test
    public void inventoryHistory() {
        this.forge();
        List<Facet> hist = inventory.getFaceHistory();
        assertEquals(1, hist.size());
    }


}
