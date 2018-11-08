package com.github.l3pi.rule;
import com.github.l3pi.type.FacetType;

import java.util.HashMap;

public final class RuleSet {
    private final HashMap<FacetType,FacetRule> facetRule;

    public RuleSet(HashMap<FacetType,FacetRule> facetRule) {
        this.facetRule = facetRule;
    }

    public HashMap<FacetType, FacetRule> getFacetRule() {
        return facetRule;
    }
}
