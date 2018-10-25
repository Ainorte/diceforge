package com.github.l3pi.game;

import com.github.l3pi.rule.RuleSet;

import java.util.ArrayList;
import java.util.List;

public class FacetRuleManager {
    private final RuleSet rules;

    public FacetRuleManager(RuleSet rules) {
        this.rules = rules;
    }

    public List<Operation> resolve(List<Facet> facets) {
        List<Operation> ops = new ArrayList<>();

        for (Facet f : facets) {
            ops.addAll(
                this.rules.facetRule
                    .get(f.facetType)
                    .getOperations(f)
            );
        }

        return ops;
    }
}
