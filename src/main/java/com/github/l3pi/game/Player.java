package com.github.l3pi.game;

import com.github.l3pi.sys.Log;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.List;

/**
 * Class tasked with handling "what a player is", including his behaviour.
 * <p>
 * Base interface-type class for every AI that may be built.
 * <p>
 * classe abstraite pour représenté un joueur
 */
public abstract class Player implements Comparable<Player> {

    private int id;

    public Player(int number) {
        this.id = number;
    }

    /**
     * @param p c'est le joueur a comparer avec ce joueur lui meme , on compare les noms
     */
    @Override
    public int compareTo(Player p) {
        return Integer.compare(getId(), p.getId());
    }

    /**
     * retourne le nom du joueur
     */

    public String getName() {
        return this.getClass().getSimpleName() + "#" + id;
    }

    public int getId() {
        return id;
    }

    /**
     * ce sont les méthodes a implementer pour chaque instance de cette classe
     * <p>
     * chooseFacetToForge la facon de choisir une face de dé
     * chooseCard la facon de choisir une carte
     * tradeGoldForGlory action pour echanger type de ressource
     * chooseResource choisir le type de ressource pour acheter , changer dans l'inventaire
     * chooseDice choisir une des dé
     * forgeDice le joueur demande a l'inventaire d'invoquer cette methode pour forger une face de dé
     */

    public abstract Facet chooseFacetToForge(List<Facet> facetList, Game game);

    public abstract Facet chooseFacetToApply(List<Facet> facetList, Game game);

    public abstract Card chooseCard(Game game);

    public abstract int chooseAction(Game game);

    public abstract boolean tradeGoldForGlory(Game game);

    public abstract ResourceType chooseResource(List<ResourceType> resources);

    public abstract int chooseDice(List<Dice> dices);

    public abstract int[] forgeDice(Game game, Facet facet);

    public abstract Tuple<Integer, Integer> chooseGoldRepartion(Inventory inventory, int gold);

    public String toFmtString() {
        return Log.State.fmt(Log.State.BLUE, toString());
    }

    public abstract boolean moreAction(Game game);

    @Override
    public String toString() {
        return Log.State.BLUE + getName();
    }
}
