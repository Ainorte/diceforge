package com.github.l3pi.game;

import com.github.l3pi.type.FacetType;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class FacetRuleManagerTest {
    private FacetRuleManager facetRuleManager;

    @Before
    public void setUp() {
        facetRuleManager = new FacetRuleManager(
            RuleFactory.getInstance().getRules(0)
        );
    }

    @Test
    public void resolve() {
        List<Operation> ops = facetRuleManager.resolve(Collections.singletonList(new Facet(FacetType.GOLD, 1)));

        assertTrue(ops.size() > 0);
    }
}
