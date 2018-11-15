package com.github.l3pi.rule;

import com.github.l3pi.game.Facet;
import com.github.l3pi.game.Operation;

import java.util.List;

/**
 * The rule providing a set of operations to apply to the game state, following the facet type and count.
 */
public interface FacetRule {
    List<Operation> getOperations(Facet facet);
}
