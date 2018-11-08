package com.github.l3pi.game;

import java.util.List;

public abstract class Player {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public abstract Facet chooseDiceFacet(Game game);

    public abstract int[] forgeMyDice(Game game);

    @Override
    public String toString() {
        return String.format("Name : %s", name);
    }
}
