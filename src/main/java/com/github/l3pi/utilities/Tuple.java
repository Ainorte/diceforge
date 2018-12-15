package com.github.l3pi.utilities;

public class Tuple<X, Y> {
    /**
     * représente un type tuple dans java utilisé dans Game.java
     */

    private final X x;
    private final Y y;

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}
