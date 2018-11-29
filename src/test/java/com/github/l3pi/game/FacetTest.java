package com.github.l3pi.game;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;


public class FacetTest {

    private Facet facet;

    @Before
    public void setUp(){
        this.facet = new Facet("1 Gold", ((Game game, Player player) -> {
            game.getInventory(player).addResources(ResourceType.GOLD, 1);
        }));

    }

    @Test
    public void testMethod(){
        assertTrue(facet.getName() == "1 Gold");

        Operation op = this.facet.getOperation();
        assertEquals(op,this.facet.getOperation());
    }
}
