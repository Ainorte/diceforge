package com.github.l3pi.game;

import com.github.l3pi.rule.FacetRule;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.FacetType;

import java.util.HashMap;

public class RuleFactory {
    private static RuleFactory ourInstance = new RuleFactory();

    public static RuleFactory getInstance() {
        return ourInstance;
    }

    private RuleFactory() {
    }

    public RuleSet getRules(int playerCount){
        HashMap<FacetType, FacetRule> facetRules = new HashMap<FacetType, FacetRule>();

        return new RuleSet(facetRules);
    }
}
