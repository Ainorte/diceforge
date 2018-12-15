package com.github.l3pi.game;

/**
 * A facet is a name and an operation
 *
 * @see Operation
 */
public final class Facet {
    private String name;
    private Operation operation;
    private int goldCost;

    /**
     * Cette classe représente une face de dé
     *
     * @param operation c'est le lambda expression qui applique les effects du dé
     * @param name      c'est le nom du dé
     * @param goldCost  Cout en gold de la facet, Par defaut à 0;
     *                  <p>
     *                  Nous supposons que chaque face de dé a son effect , par example le Gold 1 a pour lambda expression qui a
     *                  comme effect d'ajouter 1 de gold dans l'inventaire du joueur le possedant
     */
    public Facet(String name, int goldCost, Operation operation) {
        this.name = name;
        this.operation = operation;
        this.goldCost = goldCost;
    }

    public Facet(String name, Operation operation) {
        this(name, 0, operation);
    }

    /**
     * retourne le lambda expression de la face du dé
     **/

    public Operation getOperation() {
        return operation;
    }

    /**
     * retourne le nom de la face du dé
     */
    public String getName() {
        return this.name;
    }

    public int getGoldCost() {
        return goldCost;
    }

    @Override
    public String toString() {
        return name;
    }
}
