package com.github.l3pi.game;

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

    public abstract int[] forgeMyDice(Game game,Facet facet);

    @Override
    public String toString() {
        return String.format("Name : %s", name);
    }
}
