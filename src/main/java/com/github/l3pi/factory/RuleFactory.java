package com.github.l3pi.factory;

import com.github.l3pi.game.Game;
import com.github.l3pi.game.Operation;
import com.github.l3pi.game.TempPlayer;
import com.github.l3pi.rule.FacetRule;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.FacetType;
import com.github.l3pi.type.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class RuleFactory {
    private static RuleFactory ourInstance = new RuleFactory();

    public static RuleFactory getInstance() {
        return ourInstance;
    }

    private RuleFactory() {
    }

    public RuleSet getRules(int playerCount) {
        HashMap<FacetType, FacetRule> facetRules = new HashMap<FacetType, FacetRule>() {
            {
                put(FacetType.GOLD, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        game.getInventory(currentPlayer.getPlayer()).addResources(ResourceType.GOLD, facet.getCount(FacetType.GOLD))
                    );
                }});

                put(FacetType.LUNAR, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        game.getInventory(currentPlayer.getPlayer()).addResources(ResourceType.LUNAR, facet.getCount(FacetType.LUNAR))
                    );
                }});

                put(FacetType.SOLAR, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        game.getInventory(currentPlayer.getPlayer()).addResources(ResourceType.SOLAR, facet.getCount(FacetType.SOLAR))
                    );
                }});

                put(FacetType.GLORY, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        game.getInventory(currentPlayer.getPlayer()).addResources(ResourceType.GLORY, facet.getCount(FacetType.GLORY))
                    );
                }});
            }
        };

        return new RuleSet(facetRules);
    }
}
