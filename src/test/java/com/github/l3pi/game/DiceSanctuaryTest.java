package com.github.l3pi.game;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DiceSanctuaryTest {
    private DiceSanctuary diceSanctuary;

    @Before
    public void setUp() {
        this.diceSanctuary = new DiceSanctuary();}

    @Test
    public void TestAvailableFacet(){
        List<Facet> cards = this.diceSanctuary.getAvailableInventory();

        assertTrue(cards.size()==15);
    }


    @Test
    public void testPurshasableFacet(){
        List<Facet> cards = this.diceSanctuary.getPurchasableInventory(12);

        assertTrue(cards.size()==12);

        List<Facet> cards2 = this.diceSanctuary.getPurchasableInventory(0);

        assertTrue(cards2.size()==0);
    }


}
