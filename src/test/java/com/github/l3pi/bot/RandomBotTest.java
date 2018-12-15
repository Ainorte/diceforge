package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.github.l3pi.game.Factory.generateDices;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RandomBotTest {

    private Player bot;
    private List<Facet> dices;
    private List<Card> cards;
    private Game game;
    @Before
    public void setUp(){
        this.bot = new RandomBot(1);
        this.dices = Factory.generateDiceSanctuary(new ArrayList<Player>() {{
            add(bot);
        }}).getAvailableFacets();
        this.cards = Factory.generateCardSanctuary(new ArrayList<Player>() {{
            add(bot);
        }}).getAvailableCard();
        this.game = mock(Game.class);

        DiceSanctuary diceSanctuary = mock(DiceSanctuary.class);
        Inventory inventory = mock(Inventory.class);
        CardSanctuary cardSanctuary = mock(CardSanctuary.class);

        when(game.getDiceSanctuary()).thenReturn(diceSanctuary);
        when(diceSanctuary.getAvailableFacets()).thenReturn(dices);
        when(game.getInventory(bot)).thenReturn(inventory);
        when(inventory.getDices()).thenReturn(generateDices());
        when(game.getCardSanctuary()).thenReturn(cardSanctuary);
        when(cardSanctuary.getAvailableCard()).thenReturn(cards);
        when(cardSanctuary.getPurchasableCard(any())).thenReturn(cards);
    }

    @Test
    public void chooseFacetToForge() {
        Facet result = bot.chooseFacetToForge(dices, game);
        assertTrue(result == null || dices.stream().anyMatch(facet -> facet == result));
    }

    @Test
    public void chooseCard() {
        Card result = bot.chooseCard(game);
        assertTrue(result == null || cards.stream().anyMatch(card -> card == result));
    }

    @Test
    public void chooseAction() {
        int result = bot.chooseAction(game);
        assertTrue(result == 0 || result == 1);
    }

    @Test
    public void chooseResource() {
        List<ResourceType> ressources = new ArrayList<>();
        ressources.add(ResourceType.LUNAR);
        assertSame(bot.chooseResource(ressources), ResourceType.LUNAR);
    }

    @Test
    public void chooseDice() {
        List<Dice> dices = generateDices();
        int result = bot.chooseDice(dices);
        assertTrue(result == 0 || result == 1 || result == 2);

    }

    @Test
    public void forgeMyDice() {
        Facet facet =  new Facet("1 GOLD",
            ((Game game, Player player) -> game.getInventory(player).addGold(player, 1)));
        assertEquals(2, bot.forgeDice(game, facet).length);
    }

}
