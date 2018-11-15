package com.github.l3pi.game;

import com.github.l3pi.type.FacetType;
import com.github.l3pi.type.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class made to provide a shop service to the game, managing its own inventory.
 */
public class DiceSanctuary {

    private HashMap<Facet, Item> diceSanctuary = new HashMap<>();

    DiceSanctuary() {
        diceSanctuary.put(new Facet(FacetType.GOLD, 3), new Item(4, 2));
        diceSanctuary.put(new Facet(FacetType.GOLD, 4), new Item(4, 3));
        diceSanctuary.put(new Facet(FacetType.GOLD, 6), new Item(1, 4));

        diceSanctuary.put(new Facet(FacetType.SOLAR, 1), new Item(4, 3));
        diceSanctuary.put(new Facet(FacetType.SOLAR, 2), new Item(4, 8));

        diceSanctuary.put(new Facet(FacetType.LUNAR, 1), new Item(4, 2));
        diceSanctuary.put(new Facet(FacetType.LUNAR, 2), new Item(4, 6));

        diceSanctuary.put(new Facet(FacetType.GLORY, 3), new Item(4, 8));

        diceSanctuary.put(new Facet(new HashMap<FacetType, Integer>() {{
            put(FacetType.GLORY, 1);
            put(FacetType.SOLAR, 1);
        }}), new Item(1, 4));
        diceSanctuary.put(new Facet(new HashMap<FacetType, Integer>() {{
            put(FacetType.GOLD, 2);
            put(FacetType.LUNAR, 1);
        }}), new Item(1, 4));
        diceSanctuary.put(new Facet(new HashMap<FacetType, Integer>() {{
            put(FacetType.GLORY, 2);
            put(FacetType.LUNAR, 2);
        }}), new Item(1, 12));
        diceSanctuary.put(new Facet(new HashMap<FacetType, Integer>() {{
            put(FacetType.GLORY, 1);
            put(FacetType.SOLAR, 1);
            put(FacetType.LUNAR, 1);
            put(FacetType.GOLD, 1);
        }}), new Item(1, 12));

    }

    public List<Facet> getAvailableInventory() {
        return diceSanctuary.entrySet().stream().filter(entry -> entry.getValue().getCount() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public List<Facet> getPurchasableInventory(int gold) {
        return diceSanctuary.entrySet().stream().filter(entry -> (entry.getValue().getCount() > 0 && entry.getValue().isItemPurchable(gold)))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    void buyFacet(Inventory inventory, Facet facet) {
        if (facet != null) {
            if (diceSanctuary.get(facet).getCount() > 0) {
                diceSanctuary.get(facet).decreaseCount();
                inventory.addResources(ResourceType.GOLD, -(diceSanctuary.get(facet).getPrice()));
                inventory.forge(facet);
            }
        }
    }

    HashMap<Facet, Item> getDiceSanctuary() {
        return diceSanctuary;
    }
}
