package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import java.util.*;

public class Inventory {
    private final Facet[][] dices;
    private final HashMap<ResourceType, Integer> resources;
    private final Random random;


    public Inventory(Facet[][] dices, HashMap<ResourceType, Integer> resources) {
        this.dices = dices;
        this.resources = resources;
        this.random = new Random();
    }

    public List<Facet> throwDice() {
        List<Facet> facets = new ArrayList<Facet>();
        for (Facet[] dice : dices) {
            facets.add(dice[random.nextInt(dice.length)]);
        }
        return facets;
    }

    public int getRessouce(ResourceType resourceType) {
        return resources.get(resourceType);
    }

    public void addResources(ResourceType resourceType, int value) {
        resources.merge(resourceType, value, Integer::sum);
    }

    @Override
    public String toString() {
        return String.format("Dices : %s,\nRessouces : %s", Arrays.deepToString(dices),resources);
    }
}
