package com.github.l3pi.factory;

import com.github.l3pi.game.Facet;
import com.github.l3pi.game.Game;
import com.github.l3pi.game.Inventory;
import com.github.l3pi.game.Player;
import com.github.l3pi.type.FacetType;
import com.github.l3pi.type.ResourceType;

import java.util.HashMap;

public class InventoryFactory {
    private static InventoryFactory ourInstance = new InventoryFactory();

    public static InventoryFactory getInstance() {
        return ourInstance;
    }

    private InventoryFactory() {
    }

    public Inventory getInventory(Game game, Player player, int playerIndex) {
        return new Inventory(game,player,getInitialDices(), getRessousces(playerIndex));
    }

    private Facet[][] getInitialDices() {
        return new Facet[][]{
            new Facet[]{
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.SOLAR, 1)
            },
            new Facet[]{
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.LUNAR, 1),
                new Facet(FacetType.GLORY, 2),
            }
        };
    }

    private HashMap<ResourceType, Integer> getRessousces(int playerIndex) {
        return new HashMap<ResourceType, Integer>() {{
            put(ResourceType.GOLD, getGoldCount(playerIndex));
            put(ResourceType.LUNAR, 0);
            put(ResourceType.SOLAR, 0);
            put(ResourceType.GLORY, 0);
        }};
    }

    private int getGoldCount(int playerIndex) {
        switch (playerIndex) {
            case 0:
                return 3;
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return 0;
        }
    }
}
