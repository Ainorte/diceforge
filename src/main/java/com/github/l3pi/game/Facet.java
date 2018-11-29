package com.github.l3pi.game;

/**
 * A facet is a name and an operation
 *
 * @see Operation
 */
public final class Facet {
    private String name;
    private Operation operation;


    public Facet(String name, Operation operation) {
        this.name = name;
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
