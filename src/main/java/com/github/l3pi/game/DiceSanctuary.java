package com.github.l3pi.game;

import com.github.l3pi.sys.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class made to provide a shop service to the game, managing its own inventory.
 */
public class DiceSanctuary {

    private HashMap<Facet, Integer> diceSanctuary;

    public DiceSanctuary(HashMap<Facet, Integer> diceSanctuary) {
        this.diceSanctuary = new HashMap<>(diceSanctuary);
    }


    /**
     * getAvailableFacets retourne les face de dé restante dans le sanctuaire
     */
    public List<Facet> getAvailableFacets() {
        return diceSanctuary.entrySet().stream().filter(entry -> entry.getValue() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * getPurchasableFacets retourne une liste de face de dé achetable avec un prix donné en argument
     *
     * @param gold le potentiel pouvoir d'achat
     */

    public List<Facet> getPurchasableFacets(int gold) {
        return diceSanctuary.entrySet().stream().filter(entry -> (entry.getValue()) > 0 && entry.getKey().getGoldCost() <= gold)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * buyFacet retourne la face de dés acheté dans le sanctuaire
     *
     * @param facet la face de dés séléctionné pour l'achat dans le sanctuaire
     */


    Facet buyFacet(Facet facet) {
        if (diceSanctuary.getOrDefault(facet, 0) > 0) {
            diceSanctuary.merge(facet, 1, Math::subtractExact);
            return facet;
        }
        return null;
    }

    @Override
    public String toString() {
        return Log.State.fmt(Log.State.GREEN, diceSanctuary.entrySet().stream()
            .map(facet -> String.format("\tÉtat de facette de dé \"%s\": Prix: %s, Quantité disponible: %s", facet.getKey(), facet.getKey().getGoldCost(), facet.getValue()))
            .collect(Collectors.joining("\n")));
    }
}
