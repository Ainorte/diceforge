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
        List<Facet> facets = this.diceSanctuary.getAvailableInventory();
        assertEquals(15, facets.size());
    }

    @Test
    public void testPurshasableFacet(){
        List<Facet> facets = this.diceSanctuary.getPurchasableInventory(12);

        assertEquals(15, facets.size());

        List<Facet> facets2 = this.diceSanctuary.getPurchasableInventory(0);

        assertEquals(0, facets2.size());
    }


}
