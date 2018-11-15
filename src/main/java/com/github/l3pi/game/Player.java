package com.github.l3pi.game;

/**
 * Class tasked with handling "what a player is", including his behaviour.
 * <p>
 * Base interface-type class for every AI that may be built.
 */
public abstract class Player {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract Facet chooseDiceFacet(Game game);

    public abstract int[] forgeMyDice(Game game);

    @Override
    public String toString() {
        return String.format("Name : %s", name);
    }
}
