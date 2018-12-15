package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    private Game game1;
    private Game game2;
    private Game game3;
    private TreeMap<Player, Inventory> players;
    private Player p1;
    private Player p2;
    private DiceSanctuary diceSanctuary;
    private CardSanctuary cardSanctuary;

    @Before
    public void setUp() {
        p1 = mock(Player.class);
        p2 = mock(Player.class);

        players = new TreeMap<>();
        players.put(p1, mock(Inventory.class));
        players.put(p2, mock(Inventory.class));

        diceSanctuary = mock(DiceSanctuary.class);
        cardSanctuary = mock(CardSanctuary.class);

        game1 = new Game(players, diceSanctuary, cardSanctuary, 1, 7);
        game2 = new Game(players, diceSanctuary, cardSanctuary, 8, 9);
        game3 = new Game(players, diceSanctuary, cardSanctuary, 5, 5);
    }

    @Test
    public void getRound() {
        assertEquals(1, game1.getRound());
        assertEquals(8, game2.getRound());
        assertEquals(5, game3.getRound());

        game1.round();
        game2.round();
        game3.round();

        assertEquals(2, game1.getRound());
        assertEquals(9, game2.getRound());
        assertEquals(5, game3.getRound());

    }

    @Test
    public void isFinish() {
        assertFalse(game1.isFinish());
        assertFalse(game2.isFinish());
        assertTrue(game3.isFinish());

        game1.round();
        game2.round();
        game2.round();

        assertFalse(game1.isFinish());
        assertTrue(game2.isFinish());
        assertTrue(game3.isFinish());
    }

    @Test
    public void round() {

        //End Game
        game3.round();

        assertEquals(5, game3.getRound());
        verifyZeroInteractions(players.get(p1));
        verifyZeroInteractions(players.get(p2));
        verifyZeroInteractions(diceSanctuary);
        verifyZeroInteractions(cardSanctuary);

        //facets mock
        Operation opf1 = mock(Operation.class);
        Operation opf2 = mock(Operation.class);
        Operation opf3 = mock(Operation.class);
        Operation opf4 = mock(Operation.class);
        Operation opf5 = mock(Operation.class);
        Operation opf6 = mock(Operation.class);

        List<Facet> facets1 = new ArrayList<Facet>() {{
            add(new Facet("", opf1));
            add(new Facet("", opf2));
        }};
        List<Facet> facets2 = new ArrayList<Facet>() {{
            add(new Facet("", opf3));
            add(new Facet("", opf4));
        }};
        List<Facet> facets3 = new ArrayList<Facet>() {{
            add(new Facet("", 5, opf5));
            add(new Facet("", 2, opf6));
        }};
        List<Facet> facets4 = facets3.stream().filter(facet -> facet.getGoldCost() <= 3).collect(Collectors.toList());

        //dices mock
        when(players.get(p1).throwDices()).thenReturn(facets1);
        when(players.get(p2).throwDices()).thenReturn(facets2);
        when(players.get(p1).getFaceUp()).thenReturn(facets1);
        when(players.get(p2).getFaceUp()).thenReturn(facets2);

        //diceSanctuary mock
        //when(diceSanctuary.getAvailableFacets()).thenReturn(facets3);
        when(diceSanctuary.getPurchasableFacets(3)).thenReturn(facets4);
        when(diceSanctuary.buyFacet(facets4.get(0))).thenReturn(facets4.get(0));

        //cards mock
        Card card1 = mock(Card.class);
        Card card2 = mock(Card.class);
        Card card3 = mock(Card.class);
        Card card4 = mock(Card.class);
        Card card5 = mock(Card.class);

        when(card1.isRecurrent()).thenReturn(true);
        when(card2.isRecurrent()).thenReturn(true);
        when(card3.isRecurrent()).thenReturn(false);
        when(card4.isRecurrent()).thenReturn(false);
        when(card5.isRecurrent()).thenReturn(false);

        Operation opc1 = mock(Operation.class);
        Operation opc2 = mock(Operation.class);
        Operation opc3 = mock(Operation.class);
        Operation opc4 = mock(Operation.class);
        Operation opc5 = mock(Operation.class);
        Operation opc6 = mock(Operation.class);

        when(card1.getOperation()).thenReturn(opc1);
        when(card2.getOperation()).thenReturn(opc2);
        when(card5.getOperation()).thenReturn(opc5);

        when(card5.getLocation()).thenReturn(CardLocationType.SOLAR1);

        List<Card> cards2 = new ArrayList<Card>() {{
            add(card5);
        }};

        when(card5.getPrice()).thenReturn(2);
        when(card5.getResourceType()).thenReturn(new ArrayList<ResourceType>() {{
            add(ResourceType.SOLAR);
        }});

        //inventory mock
        when(players.get(p1).getResource(ResourceType.GOLD)).thenReturn(3);
        when(players.get(p1).getResource(ResourceType.SOLAR)).thenReturn(4);
        when(players.get(p2).getResource(ResourceType.SOLAR)).thenReturn(2);

        when(players.get(p1).getCardInventory()).thenReturn(new ArrayList<Card>() {{
            add(card1);
            add(card3);
        }});
        when(players.get(p2).getCardInventory()).thenReturn(new ArrayList<Card>() {{
            add(card2);
            add(card4);
        }});

        //player mock
        when(p1.chooseAction(any())).thenReturn(0).thenReturn(1);
        when(p1.chooseFacetToForge(facets4, game2)).thenReturn(facets4.get(0)).thenReturn(facets3.get(0));
        when(p1.forgeDice(game2, facets4.get(0))).thenReturn(new int[]{1, 2});
        when(p1.moreAction(game2)).thenReturn(true);
        when(p1.chooseCard(game2)).thenReturn(cards2.get(0));

        when(p2.chooseAction(game2)).thenReturn(1);
        when(p2.chooseCard(game2)).thenReturn(cards2.get(0));

        //CardSantiary mock

        when(cardSanctuary.buyCard(card5)).thenReturn(card5);
        when(cardSanctuary.move(CardLocationType.SOLAR1, p1)).thenReturn(null);
        when(cardSanctuary.move(CardLocationType.SOLAR1, p2)).thenReturn(p1);

        game2.round();

        /* player 1*/
        //divineBlessing
        InOrder order = inOrder(p1, p2, players.get(p1), players.get(p2), diceSanctuary, cardSanctuary, opf1, opf2, opf3, opf4, opc1, opc2, opc3, opc4, opc1, opc2, opc3, opc4, opc5, opc6);
        order.verify(players.get(p1), times(1)).throwDices();
        order.verify(players.get(p2), times(1)).throwDices();
        order.verify(opf1, times(1)).apply(game2, p1);
        order.verify(opf2, times(1)).apply(game2, p1);
        order.verify(opf3, times(1)).apply(game2, p2);
        order.verify(opf4, times(1)).apply(game2, p2);

        order.verify(players.get(p1), times(1)).throwDices();
        order.verify(players.get(p2), times(1)).throwDices();
        order.verify(opf1, times(1)).apply(game2, p1);
        order.verify(opf2, times(1)).apply(game2, p1);
        order.verify(opf3, times(1)).apply(game2, p2);
        order.verify(opf4, times(1)).apply(game2, p2);

        //recurrentAction
        order.verify(opc1, times(1)).apply(game2, p1);
        order.verify(opc3, times(0)).apply(any(), any());

        //chooseDice
        order.verify(p1, times(1)).chooseAction(game2);
        order.verify(p1, times(1)).chooseFacetToForge(facets4, game2);
        order.verify(diceSanctuary, times(1)).buyFacet(facets4.get(0));
        order.verify(players.get(p1), times(1)).addResources(ResourceType.GOLD, -2);
        order.verify(p1, times(1)).forgeDice(game2, facets4.get(0));
        order.verify(players.get(p1), times(1)).forge(facets4.get(0), 1, 2);
        order.verify(p1, times(1)).chooseFacetToForge(facets4, game2);
        order.verify(diceSanctuary, times(1)).buyFacet(facets3.get(0));
        order.verify(players.get(p1), times(0)).addResources(ResourceType.GOLD, -5);
        order.verify(p1, times(0)).forgeDice(game2, facets3.get(0));
        order.verify(players.get(p1), times(0)).forge(facets3.get(0), 1, 2);

        //Second action
        order.verify(p1, times(1)).moreAction(game2);
        order.verify(players.get(p1), times(1)).addResources(ResourceType.SOLAR, -2);

        //buyCard
        order.verify(p1, times(1)).chooseAction(game2);
        order.verify(p1, times(1)).chooseCard(game2);
        order.verify(cardSanctuary, times(1)).buyCard(card5);
        order.verify(players.get(p1), times(1)).addResources(ResourceType.SOLAR, -2);
        order.verify(cardSanctuary, times(1)).move(CardLocationType.SOLAR1, p1);
        order.verify(players.get(p1), times(1)).addCard(card5);
        order.verify(opc5, times(1)).apply(game2, p1);

        /*player 2*/
        //divineBlessing
        order.verify(players.get(p1), times(1)).throwDices();
        order.verify(players.get(p2), times(1)).throwDices();
        order.verify(opf1, times(1)).apply(game2, p1);
        order.verify(opf2, times(1)).apply(game2, p1);
        order.verify(opf3, times(1)).apply(game2, p2);
        order.verify(opf4, times(1)).apply(game2, p2);

        order.verify(players.get(p1), times(1)).throwDices();
        order.verify(players.get(p2), times(1)).throwDices();
        order.verify(opf1, times(1)).apply(game2, p1);
        order.verify(opf2, times(1)).apply(game2, p1);
        order.verify(opf3, times(1)).apply(game2, p2);
        order.verify(opf4, times(1)).apply(game2, p2);

        //recurrentAction
        order.verify(opc2, times(1)).apply(game2, p2);
        order.verify(opc4, times(0)).apply(any(), any());

        //buyCard
        order.verify(p2, times(1)).chooseAction(game2);
        order.verify(p2, times(1)).chooseCard(game2);
        order.verify(cardSanctuary, times(1)).buyCard(card5);
        order.verify(players.get(p2), times(1)).addResources(ResourceType.SOLAR, -2);
        order.verify(cardSanctuary, times(1)).move(CardLocationType.SOLAR1, p2);
        order.verify(players.get(p2), times(1)).addCard(card5);
        order.verify(opc5, times(1)).apply(game2, p2);
        //Chasse
        order.verify(players.get(p1), times(1)).throwDices();
        order.verify(opf1, times(1)).apply(game2, p1);
        order.verify(opf2, times(1)).apply(game2, p1);

        //No Second action
        order.verify(p2, times(1)).moreAction(game2);
        order.verify(players.get(p2), times(0)).addResources(ResourceType.SOLAR, -2);

        assertEquals(9, game2.getRound());
    }

    @Test
    public void getDiceSanctuary() {
        assertEquals(diceSanctuary, game1.getDiceSanctuary());
    }

    @Test
    public void getCardSanctuary() {
        assertEquals(cardSanctuary, game1.getCardSanctuary());
    }

    @Test
    public void getPlayers() {
        List<Player> ps = new ArrayList<Player>() {{
            add(p1);
            add(p2);
        }};
        assertEquals(ps, game1.getPlayers());
    }

    @Test
    public void getPlayer() {
        when(p1.getId()).thenReturn(1);
        when(p2.getId()).thenReturn(2);

        assertEquals(p1, game1.getPlayer(1));
        assertEquals(p2, game1.getPlayer(2));
    }

    @Test
    public void getInventory() {
        assertEquals(players.get(p1), game1.getInventory(p1));
        assertEquals(players.get(p2), game1.getInventory(p2));
    }

    @Test
    public void getInventories() {
        when(p1.getId()).thenReturn(1);
        when(p2.getId()).thenReturn(2);

        HashMap<Integer, Inventory> result = new HashMap<>();
        result.put(p1.getId(), players.get(p1));
        result.put(p2.getId(), players.get(p2));

        assertEquals(result, game1.getInventories());
    }

    @Test
    public void getBestPlayer() {
        when(players.get(p1).getResource(ResourceType.GLORY)).thenReturn(5);
        when(players.get(p2).getResource(ResourceType.GLORY)).thenReturn(10);

        List<Player> win = new ArrayList<Player>() {{
            add(p2);
        }};

        assertEquals(win, game1.getBestPlayer());

        when(players.get(p1).getResource(ResourceType.GLORY)).thenReturn(30);
        when(players.get(p2).getResource(ResourceType.GLORY)).thenReturn(5);

        win = new ArrayList<Player>() {{
            add(p1);
        }};

        assertEquals(win, game1.getBestPlayer());

        when(players.get(p1).getResource(ResourceType.GLORY)).thenReturn(30);
        when(players.get(p2).getResource(ResourceType.GLORY)).thenReturn(30);

        win = new ArrayList<Player>() {{
            add(p1);
            add(p2);
        }};

        assertEquals(win, game1.getBestPlayer());
    }
}
