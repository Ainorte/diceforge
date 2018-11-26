package com.github.l3pi.game;

import com.github.l3pi.utilities.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A facet is a list of operations
 *
 * @see Operation
 */
public final class Facet {
    private String name;
    private List<Tuple<Integer, Operation>> operations;

    public Facet(String name, List<Tuple<Integer, Operation>> operations) {
        this(name, operations, true);
    }

    public Facet(String name, int order, Operation operation) {
        this(name, new ArrayList<Tuple<Integer, Operation>>() {{
            add(new Tuple<>(order, operation));
        }}, false);
    }

    private Facet(String name, List<Tuple<Integer, Operation>> operations, boolean copy) {
        this.name = name;
        this.operations = copy ? new ArrayList<>(operations) : operations;
        this.operations.sort(Comparator.comparingInt(Tuple::getX));
    }

    public List<Tuple<Integer, Operation>> getOperations() {
        return new ArrayList<>(operations);
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }
}
