package com.github.l3pi.factory;

import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryFactoryTest {
    @Test
    public void getGoldCount() {
        InventoryFactory i = InventoryFactory.getInstance();
        assertEquals(3, i.getGoldCount(0));
        assertEquals(0, i.getGoldCount(Integer.MAX_VALUE));
    }

    @Test
    public void testDefaultDicesAreTwo() {
        assertEquals(2, InventoryFactory.getInstance().getInitialDices().length);
    }
}
