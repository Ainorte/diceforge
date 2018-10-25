package com.github.l3pi.game;

import com.github.l3pi.type.FacetType;

public final class Facet {
    public final FacetType facetType;
    public final int count;

    public Facet(FacetType facetType, int count) {
        this.facetType = facetType;
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("FacetType : %s, Count : %d", facetType, count);
    }
}
