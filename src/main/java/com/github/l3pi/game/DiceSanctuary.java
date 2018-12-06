package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class made to provide a shop service to the game, managing its own inventory.
 */
public class DiceSanctuary {
    /**
     * Data class made to be tied with the Sanctuary, to allow
     * storing of every sanctuary-related metadata associated with a given item.
     *
     * @see DiceSanctuary
     *
     * la classe locale Item permet de garder l'information de la face restantes dans le sanctuaire et son prix
     */
    private static class Item {
        private int count;
        private int price;

        Item(int c, int p) {
            this.count = c;
            this.price = p;
        }

        void decreaseCount() {
            this.count -= 1;
        }

        boolean isItemPurchable(int goldCount) {
            return this.price <= goldCount;
        }

        int getCount() {
            return count;
        }

        int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return String.format("Prix: %s, Quantité disponible: %s", getPrice(), getCount());
        }
    }

    private HashMap<Facet, Item> diceSanctuary = new HashMap<>();

    DiceSanctuary() {
        diceSanctuary.put(new Facet("3 GOLD",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 3); })),
            new Item(4, 2));
        diceSanctuary.put(new Facet("4 GOLD",
            ((Game game, Player player) -> {
                 game.getInventory(player).addResources(ResourceType.GOLD, 4); })),
            new Item(4, 3));
        diceSanctuary.put(new Facet("6 GOLD",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 6); })),
            new Item(1, 4));

        diceSanctuary.put(new Facet("1 SOLAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1); })),
            new Item(4, 3));
        diceSanctuary.put(new Facet("2 SOLAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 2); })),
            new Item(4, 8));

        diceSanctuary.put(new Facet("1 LUNAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1); })),
            new Item(4, 2));
        diceSanctuary.put(new Facet("2 LUNAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 2); })),
            new Item(4, 6));

        diceSanctuary.put(new Facet("3 GLORY",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 3); })),
            new Item(4, 8));



        diceSanctuary.put(new Facet("1 GLORY + SOLAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GLORY, 1);
                game.getInventory(player).addResources(ResourceType.SOLAR, 1);})),
            new Item(1, 4));


        diceSanctuary.put(new Facet("2 GOLD + 1 LUNAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 2);
                game.getInventory(player).addResources(ResourceType.LUNAR, 1);})),
            new Item(1, 4));


        diceSanctuary.put(new Facet("2 GLORY + LUNAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GLORY, 2);
                game.getInventory(player).addResources(ResourceType.LUNAR, 2);})),
            new Item(1, 12));

        diceSanctuary.put(new Facet("1 GOLD + LUNAR + GLORY + SOLAR",
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
                game.getInventory(player).addResources(ResourceType.LUNAR, 1);
                game.getInventory(player).addResources(ResourceType.GLORY, 1);
                game.getInventory(player).addResources(ResourceType.SOLAR, 1); })),
            new Item(1, 12));


    }


    /**getAvailableInventory retourne les face de dé restante dans le sanctuaire
     *
     * */
    public List<Facet> getAvailableInventory() {
        return diceSanctuary.entrySet().stream().filter(entry -> entry.getValue().getCount() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**getPurchasableInventory retourne une liste de face de dé achetable avec un prix donné en argument
     * @param gold le potentiel pouvoir d'achat
     * */

    public List<Facet> getPurchasableInventory(int gold) {
        return diceSanctuary.entrySet().stream().filter(entry -> (entry.getValue().getCount() > 0 && entry.getValue().isItemPurchable(gold)))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**buyFacet retourne la face de dés acheté dans le sanctuaire
     * @param facet  la face de dés séléctionné pour l'achat dans le sanctuaire
     *
     * */


    Facet buyFacet(Facet facet) {
        if (facet != null) {
            if (diceSanctuary.get(facet).getCount() > 0) {
                diceSanctuary.get(facet).decreaseCount();
                return facet;
            }

        }
        return null;
    }

    /** Retourne le prix d'une face dans le sanctuaire
     * @param facet  la face de dés séléctionné
     * */

    int getPriceForFacet(Facet facet) {
        return diceSanctuary.containsKey(facet) ? diceSanctuary.get(facet).getPrice() : 0;
    }

    @Override
    public String toString() {
        return diceSanctuary.entrySet().stream()
            .map(facet -> String.format("État de facette de dé \"%s\": %s", facet.getKey(), facet.getValue()))
            .collect(Collectors.joining("\n"));
    }
}
