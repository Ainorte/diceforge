package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InventoryTest {

    private Facet f1 = new Facet("f1", (game, player) -> {
    });
    private Facet f2 = new Facet("f2", (game, player) -> {
    });

    private List<Dice> dices;

    private Dice dice1 = new Dice(new ArrayList<Facet>() {{
        add(f1);
        add(f2);
        add(f2);
        add(f2);
        add(f2);
        add(f2);
    }});
    private Dice dice2 = new Dice(new ArrayList<Facet>() {{
        add(f2);
        add(f1);
        add(f1);
        add(f1);
        add(f1);
        add(f1);
    }});

    private Inventory inventory;

    private Inventory inventoryResources;
    private HashMap<ResourceType, Integer> resources;
    private Hammer hammer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dices = new ArrayList<Dice>() {{
            add(dice1);
            add(dice2);
        }};
        inventory = new Inventory(dices);

        resources = new HashMap<ResourceType, Integer>() {{
            put(ResourceType.GLORY, 100);
            put(ResourceType.SOLAR, 6);
            put(ResourceType.LUNAR, 3);
            put(ResourceType.GOLD, 12);
        }};
        hammer = spy(new Hammer());
        inventoryResources = spy(new Inventory(resources, dices, new ArrayList<>(), new ArrayList<>(), 0, hammer));
    }

    @Test
    public void getResources() {
        assertEquals(resources, inventoryResources.getResources());
    }

    @Test
    public void getResource() {
        assertEquals(100, inventoryResources.getResource(ResourceType.GLORY));
        assertEquals(6, inventoryResources.getResource(ResourceType.SOLAR));
        assertEquals(3, inventoryResources.getResource(ResourceType.LUNAR));
        assertEquals(12, inventoryResources.getResource(ResourceType.GOLD));
    }

    @Test
    public void getMaxRessources() {

        assertEquals(Integer.MAX_VALUE, inventoryResources.getMaxRessources(ResourceType.GLORY));
        assertEquals(12, inventoryResources.getMaxRessources(ResourceType.GOLD));
        assertEquals(6, inventoryResources.getMaxRessources(ResourceType.SOLAR));
        assertEquals(6, inventoryResources.getMaxRessources(ResourceType.LUNAR));

        inventoryResources.addExtension();

        assertEquals(Integer.MAX_VALUE, inventoryResources.getMaxRessources(ResourceType.GLORY));
        assertEquals(16, inventoryResources.getMaxRessources(ResourceType.GOLD));
        assertEquals(9, inventoryResources.getMaxRessources(ResourceType.SOLAR));
        assertEquals(9, inventoryResources.getMaxRessources(ResourceType.LUNAR));

        inventoryResources.addExtension();
        inventoryResources.addExtension();

        assertEquals(Integer.MAX_VALUE, inventoryResources.getMaxRessources(ResourceType.GLORY));
        assertEquals(24, inventoryResources.getMaxRessources(ResourceType.GOLD));
        assertEquals(15, inventoryResources.getMaxRessources(ResourceType.SOLAR));
        assertEquals(15, inventoryResources.getMaxRessources(ResourceType.LUNAR));
    }

    @Test
    public void addResources() {
        inventory.addResources(ResourceType.GOLD, 2);
        assertEquals(2, inventory.getResource(ResourceType.GOLD));
        inventory.addResources(ResourceType.GLORY, 3);
        assertEquals(3, inventory.getResource(ResourceType.GLORY));
        inventory.addResources(ResourceType.LUNAR, 4);
        assertEquals(4, inventory.getResource(ResourceType.LUNAR));
        inventory.addResources(ResourceType.SOLAR, 1);
        assertEquals(1, inventory.getResource(ResourceType.SOLAR));


        inventory.addResources(ResourceType.GOLD, 10);
        assertEquals(12, inventory.getResource(ResourceType.GOLD));
        inventory.addResources(ResourceType.GLORY, 100);
        assertEquals(103, inventory.getResource(ResourceType.GLORY));
        inventory.addResources(ResourceType.LUNAR, 2);
        assertEquals(6, inventory.getResource(ResourceType.LUNAR));
        inventory.addResources(ResourceType.SOLAR, 5);
        assertEquals(6, inventory.getResource(ResourceType.SOLAR));

        inventory.addResources(ResourceType.GOLD, 1);
        assertEquals(12, inventory.getResource(ResourceType.GOLD));
        inventory.addResources(ResourceType.LUNAR, 1);
        assertEquals(6, inventory.getResource(ResourceType.LUNAR));
        inventory.addResources(ResourceType.SOLAR, 1);
        assertEquals(6, inventory.getResource(ResourceType.SOLAR));

        inventory.addExtension();

        inventory.addResources(ResourceType.GOLD, 10);
        assertEquals(16, inventory.getResource(ResourceType.GOLD));
        inventory.addResources(ResourceType.GLORY, 50);
        assertEquals(153, inventory.getResource(ResourceType.GLORY));
        inventory.addResources(ResourceType.LUNAR, 6);
        assertEquals(9, inventory.getResource(ResourceType.LUNAR));
        inventory.addResources(ResourceType.SOLAR, 6);
        assertEquals(9, inventory.getResource(ResourceType.SOLAR));

        inventory.addExtension();
        inventory.addExtension();

        inventory.addResources(ResourceType.GOLD, 8);
        assertEquals(24, inventory.getResource(ResourceType.GOLD));
        inventory.addResources(ResourceType.GLORY, 1);
        assertEquals(154, inventory.getResource(ResourceType.GLORY));
        inventory.addResources(ResourceType.LUNAR, 6);
        assertEquals(15, inventory.getResource(ResourceType.LUNAR));
        inventory.addResources(ResourceType.SOLAR, 6);
        assertEquals(15, inventory.getResource(ResourceType.SOLAR));

        inventory.addResources(ResourceType.GOLD, -4);
        assertEquals(20, inventory.getResource(ResourceType.GOLD));
        inventory.addResources(ResourceType.GLORY, -54);
        assertEquals(100, inventory.getResource(ResourceType.GLORY));
        inventory.addResources(ResourceType.LUNAR, -5);
        assertEquals(10, inventory.getResource(ResourceType.LUNAR));
        inventory.addResources(ResourceType.SOLAR, -5);
        assertEquals(10, inventory.getResource(ResourceType.SOLAR));

        inventory.addResources(ResourceType.GOLD, -21);
        assertEquals(0, inventory.getResource(ResourceType.GOLD));
        inventory.addResources(ResourceType.GLORY, -101);
        assertEquals(0, inventory.getResource(ResourceType.GLORY));
        inventory.addResources(ResourceType.LUNAR, -11);
        assertEquals(0, inventory.getResource(ResourceType.LUNAR));
        inventory.addResources(ResourceType.SOLAR, -11);
        assertEquals(0, inventory.getResource(ResourceType.SOLAR));
    }

    @Test
    public void addGold() {
        Player player = mock(Player.class,"player");

        inventoryResources.addGold(player,5);
        verify(inventoryResources, times(1)).addResources(ResourceType.GOLD,5);
        verify(hammer, times(0)).addGold(anyInt());

        inventoryResources.getHammer().addCard();

        inventoryResources.addGold(player,4);
        verify(inventoryResources, times(0)).addResources(ResourceType.GOLD,4);
        verify(hammer, times(1)).addGold(4);

        inventoryResources.addExtension();

        when(player.chooseGoldRepartion(inventoryResources,3)).thenReturn(new Tuple<>(1,2));

        inventoryResources.addGold(player,3);
        verify(player,times(1)).chooseGoldRepartion(inventoryResources,3);
        verify(inventoryResources, times(1)).addResources(ResourceType.GOLD,1);
        verify(hammer, times(1)).addGold(2);
    }

    @Test
    public void getDices() {
        assertEquals(dices, inventory.getDices());
    }

    @Test
    public void getFaceUp() {
        assertEquals(dices.stream().map(Dice::getFaceUp).collect(Collectors.toList()), inventory.getFaceUp());
    }

    @Test
    public void throwDices() {
        Dice d1 = mock(Dice.class,"d1");
        Dice d2 = mock(Dice.class,"d2");

        List<Dice> ds = new ArrayList<Dice>()
        {{
            add(d1);
            add(d2);
        }};

        Inventory inv = new Inventory(ds);

        List<Facet> result = new ArrayList<Facet>()
        {{
           add(f1);
           add(f2);
        }};

        when(d1.throwDice()).thenReturn(f1);
        when(d2.throwDice()).thenReturn(f2);

        assertEquals(result,inv.throwDices());

        verify(d1,times(1)).throwDice();
        verify(d2,times(1)).throwDice();
    }

    @Test
    public void forge() {
        Dice d1 = mock(Dice.class,"d1");
        Dice d2 = mock(Dice.class,"d2");

        List<Dice> ds = new ArrayList<Dice>()
        {{
            add(d1);
            add(d2);
        }};

        Inventory inv = new Inventory(ds);

        inv.forge(f1,0,5);
        inv.forge(f2,1,2);

        verify(d1,times(1)).forgeFacet(f1,5);
        verify(d2,times(1)).forgeFacet(f2,2);

    }

    @Test
    public void getFacetInventory() {
        List<Facet> facets = new ArrayList<Facet>()
        {{
            add(new Facet("f1", ((game, player) -> { })));
        }};
        Inventory inv = new Inventory(new HashMap<>(), new ArrayList<>(), facets, new ArrayList<>(), 0, new Hammer());
        assertEquals(facets, inv.getFacetInventory());
    }

    @Test
    public void addCard() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(5,ResourceType.LUNAR, CardLocationType.LUNAR1,(game, player) -> {},"c1"));
        inventory.addCard(cards.get(0));
        assertEquals(cards,inventory.getCardInventory());
    }

    @Test
    public void getCardInventory() {
        List<Card> cards = new ArrayList<Card>(){{add(new Card(5,ResourceType.LUNAR, CardLocationType.LUNAR1,(game, player) -> {},"c1"));}};
        Inventory inv = new Inventory(new HashMap<>(), new ArrayList<>(), new ArrayList<>(), cards, 0, new Hammer());
        assertEquals(cards, inv.getCardInventory());
    }

    @Test
    public void addExtension() {
        inventory.addExtension();
        assertEquals(Integer.MAX_VALUE, inventory.getMaxRessources(ResourceType.GLORY));
        assertEquals(16, inventory.getMaxRessources(ResourceType.GOLD));
        assertEquals(9, inventory.getMaxRessources(ResourceType.SOLAR));
        assertEquals(9, inventory.getMaxRessources(ResourceType.LUNAR));

        inventory.addExtension();
        inventory.addExtension();

        assertEquals(Integer.MAX_VALUE, inventory.getMaxRessources(ResourceType.GLORY));
        assertEquals(24, inventory.getMaxRessources(ResourceType.GOLD));
        assertEquals(15, inventory.getMaxRessources(ResourceType.SOLAR));
        assertEquals(15, inventory.getMaxRessources(ResourceType.LUNAR));

    }

    @Test
    public void getHammer() {
        Hammer hammer = new Hammer();
        Inventory inv = new Inventory(new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0, hammer);
        assertEquals(hammer, inv.getHammer());
    }
}
