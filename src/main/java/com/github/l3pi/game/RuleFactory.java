package com.github.l3pi.game;

import com.github.l3pi.rule.FacetRule;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.FacetType;
import com.github.l3pi.type.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RuleFactory {
    private static RuleFactory ourInstance = new RuleFactory();

    public static RuleFactory getInstance() {
        return ourInstance;
    }

    private RuleFactory() {
    }

    public RuleSet getRules(int playerCount){
        HashMap<FacetType, FacetRule> facetRules = new HashMap<FacetType, FacetRule>();
        facetRules.put(FacetType.GOLD, facet -> {
            List<Operation> operations = new ArrayList<Operation>();
            operations.add((Game game, TempPlayer currentPlayer) -> { currentPlayer.player.resources.merge(ResourceType.GOLD, 1, Integer::sum);return game; });
            return operations;
        });
        facetRules.put(FacetType.LUNAR, facet -> {
            List<Operation> operations = new ArrayList<Operation>();
            operations.add((Game game, TempPlayer currentPlayer) -> { currentPlayer.player.resources.merge(ResourceType.LUNAR, 1, Integer::sum);return game; });
            return operations;
        });
        facetRules.put(FacetType.SOLAR, facet -> {
            List<Operation> operations = new ArrayList<Operation>();
            operations.add((Game game, TempPlayer currentPlayer) -> { currentPlayer.player.resources.merge(ResourceType.SOLAR, 1, Integer::sum);return game; });
            return operations;
        });
        facetRules.put(FacetType.GLORY, facet -> {
            List<Operation> operations = new ArrayList<Operation>();
            operations.add((Game game, TempPlayer currentPlayer) -> { currentPlayer.player.resources.merge(ResourceType.GLORY, 1, Integer::sum);return game; });
            return operations;
        });
        return new RuleSet(facetRules);
    }
}
