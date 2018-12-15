package com.github.l3pi.game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class DiceSanctuaryTest {

    private DiceSanctuary diceSanctuary;
    private HashMap<Facet, Integer> sanctuary;
    private List<Facet> facets;

    @Before
    public void setUp() {

        facets = new ArrayList<Facet>() {{
            add(new Facet("f1", 2, (game, player) -> {
            }));
            add(new Facet("f2", 4, (game, player) -> {
            }));
            add(new Facet("f3", 2, (game, player) -> {
            }));
            add(new Facet("f4", 4, (game, player) -> {
            }));
        }};

        sanctuary = new HashMap<Facet, Integer>() {{
            put(facets.get(0), 1);
            put(facets.get(1), 4);
            put(facets.get(2), 0);
            put(facets.get(3), 0);
        }};
        diceSanctuary = new DiceSanctuary(sanctuary);
    }

    @Test
    public void getAvailableFacets() {
        assertArrayEquals(new Facet[]{facets.get(0), facets.get(1)}, diceSanctuary.getAvailableFacets().stream().sorted(Comparator.comparing(Facet::getName)).toArray());
    }

    @Test
    public void getPurchasableFacets() {
        assertArrayEquals(new Facet[]{facets.get(0), facets.get(1)}, diceSanctuary.getPurchasableFacets(4).stream().sorted(Comparator.comparing(Facet::getName)).toArray());
        assertArrayEquals(new Facet[]{facets.get(0)}, diceSanctuary.getPurchasableFacets(2).toArray());
        assertArrayEquals(new Facet[]{}, diceSanctuary.getPurchasableFacets(1).toArray());
    }

    @Test
    public void buyFacet() {
        assertEquals(facets.get(0), diceSanctuary.buyFacet(facets.get(0)));

        assertArrayEquals(new Facet[]{facets.get(1)}, diceSanctuary.getAvailableFacets().toArray());

        assertNull(diceSanctuary.buyFacet(facets.get(0)));

        assertNull(diceSanctuary.buyFacet(new Facet("", (game, player) -> {
        })));

        assertNull(diceSanctuary.buyFacet(null));
    }
}
