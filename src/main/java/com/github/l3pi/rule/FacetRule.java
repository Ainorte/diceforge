package com.github.l3pi.rule;

import com.github.l3pi.game.Facet;
import com.github.l3pi.game.Operation;

import java.util.List;

public interface FacetRule {
    public List<Operation> getOperations(Facet facet);
}
