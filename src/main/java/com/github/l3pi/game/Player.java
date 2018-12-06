package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.List;

/**
 * Class tasked with handling "what a player is", including his behaviour.
 * <p>
 * Base interface-type class for every AI that may be built.
 *
 * classe abstraite pour représenté un joueur
 * @param name c'est le nom du joueur
 */
public abstract class Player implements Comparable<Player> {

    /**@param p c'est le joueur a comparer avec ce joueur lui meme , on compare les noms
     *
     *
     * */
    @Override
    public int compareTo(Player p) {
        return getName().compareTo(p.getName());
    }

    private String name;

    public Player(String name) {
        this.name = name;
    }

    /** retourne le nom du joueur
     *
     * */

    public String getName() {
        return this.name;
    }

    /** ce sont les méthodes a implementer pour chaque instance de cette classe
     *
     * chooseDiceFacet la facon de choisir une face de dé
     * chooseCard la facon de choisir une carte
     * tradeGoldForGlory action pour echanger type de ressource
     * chooseResource choisir le type de ressource pour acheter , changer dans l'inventaire
     * chooseDice choisir une des dé
     * forgeMyDice le joueur demande a l'inventaire d'invoquer cette methode pour forger une face de dé
     *
     * */

    public abstract Facet chooseDiceFacet(Game game);

    public abstract Card chooseCard(Game game);

    public abstract int chooseAction(Game game);

    public abstract boolean tradeGoldForGlory(Game game);

    public abstract ResourceType chooseResource(List<ResourceType> resources);

    public abstract int chooseDice(List<Dice> dices);

    public abstract int[] forgeMyDice(Game game,Facet facet);

    public abstract Tuple<Integer,Integer> chooseGoldRepartion(Inventory inventory, int gold);

    @Override
    public String toString() {
        return String.format("Name : %s", name);
    }
}
