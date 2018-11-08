package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import java.util.*;

public class Inventory {
    private final Facet[][] dices;
    private final HashMap<ResourceType, Integer> resources;
    private final Random random;
    private final Facet[] faceUp;

    public Inventory(Facet[][] dices, HashMap<ResourceType, Integer> resources) {
        this.dices = dices;
        this.resources = resources;
        this.random = new Random();
        this.faceUp = new Facet[dices.length];
    }

    public List<Facet> throwDice() {
        List<Facet> facets = new ArrayList<Facet>();
        for (int i = 0 ; i < dices.length; i++) {
            int randIndex = random.nextInt(dices[i].length);
            facets.add(dices[i][randIndex]);
            faceUp[i] = dices[i][randIndex];
        }
        return facets;
    }

    public Facet[] getFaceUp(){
        return Arrays.copyOf(faceUp,faceUp.length);
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
