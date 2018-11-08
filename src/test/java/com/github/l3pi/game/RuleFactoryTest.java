package com.github.l3pi.game;

import com.github.l3pi.factory.RuleFactory;
import com.github.l3pi.rule.FacetRule;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.FacetType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Set;

import static org.junit.Assert.*;

public class RuleFactoryTest {
    private RuleSet set;

    @Before
    public void setUp() {
        set = RuleFactory.getInstance().getRules(0);
    }

    @Test
    public void getRules() {
        HashMap<FacetType, FacetRule> facetRules = set.getFacetRule();
        Set<FacetType> facets = facetRules.keySet();

        assertTrue(facets.contains(FacetType.GOLD));
        assertTrue(facets.contains(FacetType.GLORY));
        assertTrue(facets.contains(FacetType.SOLAR));
        assertTrue(facets.contains(FacetType.LUNAR));
    }
}
