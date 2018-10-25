package com.github.l3pi.rule;
import com.github.l3pi.type.FacetType;

import java.util.HashMap;

public final class RuleSet {
    public final HashMap<FacetType,FacetRule> facetRule;

    public RuleSet(HashMap<FacetType,FacetRule> facetRule) {
        this.facetRule = facetRule;
    }
}
