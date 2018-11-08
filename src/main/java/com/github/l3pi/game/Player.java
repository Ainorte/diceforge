package com.github.l3pi.game;

public abstract class Player {

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("Name : %s", name);
    }
}
