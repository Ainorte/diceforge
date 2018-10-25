package com.github.l3pi.game;

import com.github.l3pi.type.FacetType;
import com.github.l3pi.type.ResourceType;

import java.util.HashMap;

public class Player {
    public final HashMap<ResourceType, Integer> resources;
    public final Facet[][] dices;

    public Player(int goldCount){
        this.resources = new HashMap<ResourceType, Integer>();
        this.resources.put(ResourceType.GOLD,goldCount);
        this.resources.put(ResourceType.LUNAR,0);
        this.resources.put(ResourceType.SOLAR,0);
        this.resources.put(ResourceType.GLORY,0);

        this.dices = new Facet[][]{
            new Facet[]{
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.SOLAR,1)
            },
            new Facet[]{
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.GOLD, 1),
                new Facet(FacetType.LUNAR, 1),
                new Facet(FacetType.GLORY, 2)
            }
        };
    }
}
