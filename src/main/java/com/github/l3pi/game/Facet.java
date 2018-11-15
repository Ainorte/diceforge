package com.github.l3pi.game;

import com.github.l3pi.type.FacetType;

import java.util.HashMap;
import java.util.Set;

/**
 * A facet is the group between a type, and an application count.
 * <p>
 * As an example, (GOLD, 5) means 5 pieces of gold.
 *
 * @see com.github.l3pi.rule.FacetRule
 * @see Operation
 */
public final class Facet {
    private final HashMap<FacetType, Integer> facetTypes;

    public Facet(HashMap<FacetType, Integer> facetTypes) {
        this.facetTypes = facetTypes;
    }

    public Facet(FacetType facetType, int count) {
        this(new HashMap<FacetType, Integer>() {{
            put(facetType, count);
        }});
    }

    public Set<FacetType> getFacetTypes() {
        return facetTypes.keySet();
    }

    public int getCount(FacetType facetType) {
        return facetTypes.getOrDefault(facetType, 0);
    }

    @Override
    public String toString() {
        return facetTypes.toString();
    }
}
