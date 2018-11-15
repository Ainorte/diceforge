package com.github.l3pi.rule;

import com.github.l3pi.type.FacetType;

import java.util.HashMap;

/**
 * Class grouping different rule sets, associated by "Type" -&gt; "Rule"
 */
public final class RuleSet {
    private final HashMap<FacetType, FacetRule> facetRules;

    public RuleSet(HashMap<FacetType, FacetRule> facetRules) {
        this.facetRules = facetRules;
    }

    public HashMap<FacetType, FacetRule> getFacetRules() {
        return facetRules;
    }
}
