package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class CardSanctuaryTest {

    private CardSanctuary cardSanctuary;
    private List<Card> cards;
    private List<Player> players;

    @Before
    public void setUp() {

        cards = new ArrayList<Card>() {{
            add(new Card(1, ResourceType.LUNAR, CardLocationType.LUNAR1, ((game, player) -> {
            }), "card1"));
            add(new Card(2, ResourceType.SOLAR, CardLocationType.SOLAR2, ((game, player) -> {
            }), "card2"));

            ArrayList<ResourceType> ress3 = new ArrayList<ResourceType>() {{
                add(ResourceType.LUNAR);
                add(ResourceType.SOLAR);
            }};

            add(new Card(2, ress3, CardLocationType.SOLAR2, ((game, player) -> {
            }), "card3", false, ""));
        }};

        players = new ArrayList<Player>() {{
            add(mock(Player.class, "p1"));
            add(mock(Player.class, "p2"));
        }};

        HashMap<Card, Integer> sanctuary = new HashMap<Card, Integer>() {{
            put(cards.get(0), 0);
            put(cards.get(1), 1);
            put(cards.get(2), 4);
        }};

        HashMap<Player, CardLocationType> locations = new HashMap<Player, CardLocationType>() {{
            put(players.get(0), CardLocationType.SOLAR2);
            put(players.get(1), CardLocationType.CENTER);
        }};

        cardSanctuary = new CardSanctuary(sanctuary, locations);
    }

    @Test
    public void getAvailableCard() {
        assertArrayEquals(new Card[]{cards.get(1), cards.get(2)}, cardSanctuary.getAvailableCard().stream().sorted(Comparator.comparing(Card::getName)).toArray());
    }

    @Test
    public void getPurchasableCard() {
        HashMap<ResourceType, Integer> resources = new HashMap<ResourceType, Integer>() {{
            put(ResourceType.LUNAR, 1);
            put(ResourceType.SOLAR, 3);
        }};

        assertArrayEquals(new Card[]{cards.get(1)}, cardSanctuary.getPurchasableCard(resources).toArray());

        resources = new HashMap<ResourceType, Integer>() {{
            put(ResourceType.LUNAR, 3);
            put(ResourceType.SOLAR, 3);
        }};

        assertArrayEquals(new Card[]{cards.get(1), cards.get(2)}, cardSanctuary.getPurchasableCard(resources).stream().sorted(Comparator.comparing(Card::getName)).toArray());

        resources = new HashMap<ResourceType, Integer>() {{
            put(ResourceType.LUNAR, 10);
            put(ResourceType.SOLAR, 0);
        }};

        assertArrayEquals(new Card[]{}, cardSanctuary.getPurchasableCard(resources).toArray());
    }

    @Test
    public void buyCard() {
        assertEquals(cards.get(1), cardSanctuary.buyCard(cards.get(1)));

        assertArrayEquals(new Card[]{cards.get(2)}, cardSanctuary.getAvailableCard().toArray());

        assertNull(cardSanctuary.buyCard(cards.get(0)));

        assertNull(cardSanctuary.buyCard(mock(Card.class)));

        assertNull(cardSanctuary.buyCard(null));
    }

    @Test
    public void getPlayerLocation() {
        assertEquals(CardLocationType.SOLAR2, cardSanctuary.getPlayerLocation(players.get(0)));
        assertEquals(CardLocationType.CENTER, cardSanctuary.getPlayerLocation(players.get(1)));
    }

    @Test
    public void getPlayerOnLocation() {
        assertNull(cardSanctuary.getPlayerOnLocation(CardLocationType.MIDDLE));
        assertEquals(players.get(0), cardSanctuary.getPlayerOnLocation(CardLocationType.SOLAR2));
        assertEquals(players.get(1), cardSanctuary.getPlayerOnLocation(CardLocationType.CENTER));
    }

    @Test
    public void move() {
        assertNull(cardSanctuary.move(CardLocationType.MIDDLE, players.get(1)));
        assertNull(cardSanctuary.move(CardLocationType.SOLAR2, players.get(0)));
        assertEquals(players.get(0), cardSanctuary.move(CardLocationType.SOLAR2, players.get(1)));

        assertNull(cardSanctuary.move(CardLocationType.CENTER, players.get(0)));
        assertNull(cardSanctuary.move(CardLocationType.CENTER, players.get(1)));
    }
}
