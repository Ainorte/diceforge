package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;

import java.util.List;

/**
 * Class tasked with handling "what a player is", including his behaviour.
 * <p>
 * Base interface-type class for every AI that may be built.
 */
public abstract class Player implements Comparable<Player> {
    @Override
    public int compareTo(Player p) {
        return getName().compareTo(p.getName());
    }

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract Facet chooseDiceFacet(Game game);

    public abstract Card chooseCard(Game game);

    public abstract int chooseAction(Game game);

    public abstract void move(CardLocationType location);

    public abstract int chooseDice(List<Dice> dices);

    public abstract int[] forgeMyDice(Game game,Facet facet);

    @Override
    public String toString() {
        return String.format("Name : %s", name);
    }
}
