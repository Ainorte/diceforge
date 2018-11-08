package com.github.l3pi.game;

import com.github.l3pi.factory.InventoryFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InventoryTest {
    private Inventory inventory;

    @Before
    public void setUp() throws Exception {
        inventory = InventoryFactory.getInstance().getInventory(0);
    }

    @Test
    public void throwDice() {
        List<Facet> facets = inventory.throwDice();

        assertEquals(2, facets.size());
    }
}
