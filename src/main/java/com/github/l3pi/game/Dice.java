package com.github.l3pi.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Dice {

    private List<Facet> facets;

    public Dice(List<Facet> facets){
        assert (facets.size() == 6);
        this.facets = new ArrayList<>(facets);
    }

    public Facet throwDice() {
        Collections.shuffle(this.facets);
        return getFaceUp();
    }

    public Facet getFaceUp(){
        return facets.get(0);
    }


    public void addFace(Facet facetToForge,int choosenFacet){
        this.facets.set(choosenFacet,facetToForge);
    }

    public Facet getFacet(int i){
        return this.facets.get(i);
    }

    @Override
    public String toString() {
        return facets.toString();
    }
}



