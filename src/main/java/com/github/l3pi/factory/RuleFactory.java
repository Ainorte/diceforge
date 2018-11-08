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
        HashMap<FacetType, FacetRule> facetRules = new HashMap<FacetType, FacetRule>()
        {
            {
                put(FacetType.GOLD, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        currentPlayer.player.resources.merge(ResourceType.GOLD, facet.getCount(FacetType.LUNAR), Integer::sum)
                    );
                }});

                put(FacetType.LUNAR, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        currentPlayer.player.resources.merge(ResourceType.LUNAR, facet.getCount(FacetType.LUNAR), Integer::sum)
                    );
                }});

                put(FacetType.SOLAR, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        currentPlayer.player.resources.merge(ResourceType.SOLAR, facet.getCount(FacetType.LUNAR), Integer::sum)
                    );
                }});

                put(FacetType.GLORY, facet -> new ArrayList<Operation>() {{
                    add((Game game, TempPlayer currentPlayer) ->
                        currentPlayer.player.resources.merge(ResourceType.GLORY, facet.getCount(FacetType.LUNAR), Integer::sum)
                    );
                }});
            }
        };

        return new RuleSet(facetRules);
    }
}
