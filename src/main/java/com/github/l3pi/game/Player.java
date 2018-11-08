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

    abstract Facet chooseDiceFacet(List<Facet> listFacet);

    abstract void forgeMyDice(Game game,int diceNumber,int faceNumber);

    @Override
    public String toString() {
        return String.format("Name : %s", name);
    }
}
