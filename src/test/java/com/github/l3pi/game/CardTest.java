package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CardTest {

    private Card card;

    @Before
    public void setUp() {
        this.card = new Card(1, ResourceType.LUNAR,CardLocationType.LUNAR1,(Game game, Player player)->{},"Le Marteau du Forgeron");
    }

    @Test
    public void testMethod(){
        assertTrue(this.card.getPrice() == 1);
        List<ResourceType> B = new ArrayList<ResourceType>() {
        };
        B.add(ResourceType.LUNAR);
        assertEquals(B,this.card.getResourceType());
        assertEquals(CardLocationType.LUNAR1,this.card.getLocationType());
        assertTrue(this.card.getOperation() instanceof Operation);
        assertTrue(this.card.isRecurrent() == false);
        assertTrue(this.card.isCardPurchasable(0) == false);
        assertTrue(this.card.isCardPurchasable(11)==true);

    }




}


