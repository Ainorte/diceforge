package com.github.l3pi.game;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class CardSanctuaryTest {

    private CardSanctuary cardSanctuary;

    @Before
    public void setUp() {
        this.cardSanctuary = new CardSanctuary();}

    @Test
    public void TestAvailableCard(){
        List<Card> cards = this.cardSanctuary.getAvailableCard();

        assertTrue(cards.size()==15);
        }


    @Test
    public void testPurshasableCard(){
        /*List<Card> cards = this.cardSanctuary.getPurchasableCard(9999);

        assertTrue(cards.size()==15);

        List<Card> cards2 = this.cardSanctuary.getPurchasableCard(0);

        assertTrue(cards2.size()==0);*/
    }



    @Test
    public void testBuyCard(){
        List<Card> cards = this.cardSanctuary.getAvailableCard();
        for(Card card:cards) {
            for(int i=0;i<=4;i++){
                this.cardSanctuary.buyCard(card);
            }
        }
        List<Card> card2 = this.cardSanctuary.getAvailableCard();
        assertTrue(card2.size()==0);
    }

}
