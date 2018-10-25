package com.github.l3pi.game;

import com.github.l3pi.type.FacetType;
import com.github.l3pi.type.ResourceType;

import java.util.*;

public class Player {
    public final HashMap<ResourceType, Integer> resources;
    public final Facet[][] dices;
    public final String name;
    private final Random random;

    public Player(int goldCount, String name){
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
        this.name = name;
        this.random = new Random();
    }

    public List<Facet> throwDice(){
        List<Facet> facets = new ArrayList<Facet>();
        for(Facet[] dice : dices){
            facets.add(dice[random.nextInt(dice.length)]);
        }
        return facets;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("Name : %s,\nDices : %s,\nRessouces : %s", name, Arrays.deepToString(dices), resources);
    }
}
