package com.github.l3pi.game;
import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InventoryTest {

    private Inventory inventory;


    @Before
    public void setUp() {
        this.inventory = new Inventory(100);
    }

    @Test
    public void testMethods() {
        assertTrue(this.inventory.getMaxRessources(ResourceType.GLORY) == Integer.MAX_VALUE);
        assertTrue(this.inventory.getMaxRessources(ResourceType.GOLD) == 12);
        assertTrue(this.inventory.getMaxRessources(ResourceType.LUNAR) == 6);

        this.inventory.addExtension();
        assertTrue(this.inventory.getMaxRessources(ResourceType.GOLD) == 12 + 4);
        assertTrue(this.inventory.getMaxRessources(ResourceType.LUNAR) == 6 + 3);

        //throw dices

        List<Facet> facets = this.inventory.throwDices();

        for (Facet facet : facets
        ) {
            assertTrue(facet instanceof Facet);

        }

        // test for forge

        Facet f = new Facet("3 GOLD", 2, ((Game game, Player player) -> {
            game.getInventory(player).addResources(ResourceType.GOLD, 3);
        }));

        this.inventory.forge(f, 0, 2);

        assertTrue(this.inventory.getDices().get(0).getFacet(2).getName() == f.getName());

        // test getter and setters

        assertTrue((this.inventory.getDices().get(0) instanceof Dice) && (this.inventory.getDices().get(1) instanceof Dice));

        assertTrue((this.inventory.getFaceUp().get(0) instanceof Facet) && (this.inventory.getFaceUp().get(1) instanceof Facet));

        assertTrue((this.inventory.getFaceUp().get(0) == this.inventory.getDices().get(0).getFaceUp()) && (this.inventory.getFaceUp().get(1) == this.inventory.getDices().get(1).getFaceUp()));

        List<Facet> facetInventory = this.inventory.getFaceInventory();

        for (Facet facet : facetInventory
        ) {
            assertTrue(facet instanceof Facet);
        }


        
        this.inventory.addExtension();

        assertTrue(this.inventory.getMaxRessources(ResourceType.LUNAR ) == 6+3*2);

        assertTrue(this.inventory.getResource(ResourceType.LUNAR) == 0);
        assertTrue(this.inventory.getResource(ResourceType.GOLD) == 100);
        assertTrue(this.inventory.getResource(ResourceType.SOLAR) == 0);
        assertTrue(this.inventory.getResource(ResourceType.GLORY) == 0);


        this.inventory.addResources(ResourceType.GOLD,10);
        this.inventory.addResources(ResourceType.LUNAR,10);
        this.inventory.addResources(ResourceType.SOLAR,10);
        this.inventory.addResources(ResourceType.GLORY,10);

        assertTrue(this.inventory.getResource(ResourceType.LUNAR) == 10);
        assertTrue(this.inventory.getResource(ResourceType.GOLD) == 110);
        assertTrue(this.inventory.getResource(ResourceType.SOLAR) == 10);
        assertTrue(this.inventory.getResource(ResourceType.GLORY) == 10);

        // test for cards
        Card card = new Card(1, ResourceType.LUNAR, CardLocationType.LUNAR1,(Game game, Player player)->{},"test");
        this.inventory.addCard(card);

        //assertTrue(this.inventory.getCards().get(this.inventory.getCards()))




    }
}
