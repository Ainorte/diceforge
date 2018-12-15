package com.github.l3pi.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dice {

    /**
     * @param facets c'est la liste des face pour constitué un dé
     */

    private List<Facet> facets;

    public Dice(List<Facet> facets) {
        assert (facets.size() == 6);
        this.facets = new ArrayList<>(facets);
    }

    /**
     * retourne une face de dés , celui qui a été lancé
     */

    Facet throwDice() {
        Collections.shuffle(this.facets);
        return getFaceUp();
    }

    /**
     * retourne la face de dé qui est face au joueur
     */

    public Facet getFaceUp() {
        return facets.get(0);
    }

    /**
     * change une face de dé en séléctionnant une face de dé et met cette face au dessus
     *
     * @param choosenFacet la face de dé choisi pour changer de face
     * @param facetToForge c'est la face a forger dans le dé
     */

    void forgeFacet(Facet facetToForge, int choosenFacet) {
        facets.set(choosenFacet, facetToForge);
        Collections.swap(facets, choosenFacet, 0);
    }

    /**
     * Selectionne une face de dé
     *
     * @return la face de dé
     */

    public Facet getFacet(int i) {
        return this.facets.get(i);
    }

    public List<Facet> getFacets() {
        return new ArrayList<>(facets);
    }

    @Override
    public String toString() {
        return facets.toString();
    }
}



