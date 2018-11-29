package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class DiceTest {

    private Dice dice;
    private Facet facet;

    @Before
    public void setUp(){
        this.facet = new Facet("1 Gold", 1, ((Game game, Player player) -> {
            game.getInventory(player).addResources(ResourceType.GOLD, 1);
        }));

        ArrayList dice1 = new ArrayList<Facet>() {{
            add(facet);
            add(facet);
            add(facet);
            add(facet);
            add(facet);
            add(facet);
        }};

        this.dice = new Dice(dice1);
    }

    @Test
    public void testMethod(){
        assertEquals(this.facet,this.dice.throwDice());
        assertEquals(this.facet,this.dice.getFaceUp());
        Facet facet2 = new Facet("1 Gold", 1, ((Game game, Player player) -> {
            game.getInventory(player).addResources(ResourceType.GOLD, 1);
        }));

        this.dice.addFace(facet2,0);
        assertEquals(facet2 , this.dice.getFaceUp());
        assertTrue(this.dice.getFaceUp() == this.dice.getFacet(0));
    }

}
