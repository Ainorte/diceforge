package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import java.util.*;

public class Inventory {
    private final Facet[][] dices;
    private final HashMap<ResourceType, Integer> resources;
    private final Random random;
    private final Facet[] faceUp;
    private final List<Facet> faceHistory;
    private final Player player;
    private final Game game;
    private int extension;

    public Inventory(Game game, Player player, Facet[][] dices, HashMap<ResourceType, Integer> resources) {
        this.dices = dices;
        this.resources = resources;
        this.random = new Random();
        this.faceUp = new Facet[]{dices[0][0],dices[1][0]};
        this.faceHistory = new ArrayList<Facet>();
        this.player = player;
        this.game = game;
    }

     public int getMaxRessources(ResourceType resourceType){
        switch (resourceType){
            case GLORY:
                return Integer.MAX_VALUE;
            case GOLD :
                return 12 + (4 * extension);
            case LUNAR: case SOLAR:
                return  6 + (3 * extension);
            default:
                return 0;
        }
     }

    public List<Facet> throwDice() {  // == Faveur Majeur
        List<Facet> facets = new ArrayList<Facet>();
        for (int i = 0 ; i < dices.length; i++) {
            int randIndex = random.nextInt(dices[i].length);
            facets.add(dices[i][randIndex]);
            faceUp[i] = dices[i][randIndex];
        }
        return facets;
    }
    public void forge(Facet facetToForge){
        if (facetToForge != null) {
            int[] choosenIndex = player.forgeMyDice(game);
            int choosenDice = choosenIndex[0];
            int choosenFacet = choosenIndex[1];
            faceHistory.add(dices[choosenDice][choosenFacet]);
            dices[choosenDice][choosenFacet] = facetToForge;
            faceUp[choosenDice] = dices[choosenDice][choosenFacet];
        }
    }



    public Facet[][] getDices() {
        return dices;
    }

    public Facet[] getFaceUp(){
        return Arrays.copyOf(faceUp,faceUp.length);
    }

    public List<Facet> getFaceHistory(){
        return faceHistory;
    }


    public int getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }

    public void addResources(ResourceType resourceType, int value) {
        int currentValue = resources.get(resourceType);
        int max = this.getMaxRessources(resourceType);
        if((currentValue + value) >=0 && (currentValue + value) < max){
            resources.merge(resourceType, value, Integer::sum);
        }
        else{
            resources.merge(resourceType, max - currentValue , Integer::sum);
        }
    }

    @Override
    public String toString() {
        return String.format("Dices : %s,\nRessouces : %s", Arrays.deepToString(dices),resources);
    }
}
