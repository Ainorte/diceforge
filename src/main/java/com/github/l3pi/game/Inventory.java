package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import static com.github.l3pi.sys.LogDAO.log;

import java.util.*;

/**
 * Class tasked with storing a player's inventory (every associated chunk of data), including, but not limited to,
 * resources, dices.
 */
public class Inventory {
    private final Facet[][] dices;
    private final HashMap<ResourceType, Integer> resources;
    private final Random random;
    private final Facet[] faceUp;
    private final List<Facet> faceHistory;
    private final Player player;
    private final Game game;

    public Inventory(Game game, Player player, Facet[][] dices, HashMap<ResourceType, Integer> resources) {
        this.dices = dices;
        this.resources = resources;
        this.random = new Random();
        this.faceUp = new Facet[]{dices[0][0], dices[1][0]};
        this.faceHistory = new ArrayList<Facet>();
        this.player = player;
        this.game = game;
    }

    public List<Facet> throwDice() {  // == Faveur Majeur
        List<Facet> facets = new ArrayList<Facet>();
        for (int i = 0; i < dices.length; i++) {
            int randIndex = random.nextInt(dices[i].length);
            facets.add(dices[i][randIndex]);
            faceUp[i] = dices[i][randIndex];
        }

        log(this.player + " a acheté " + facets);
        return facets;
    }
    public void forge(Facet facetToForge,int choosenDice,int choosenFacet){
        if (facetToForge != null) {
            faceHistory.add(dices[choosenDice][choosenFacet]);
            dices[choosenDice][choosenFacet] = facetToForge;
            faceUp[choosenDice] = dices[choosenDice][choosenFacet];
        }
    }


    public Facet[][] getDices() {
        return dices;
    }

    public Facet[] getFaceUp() {
        return Arrays.copyOf(faceUp, faceUp.length);
    }

    public List<Facet> getFaceHistory() {
        return faceHistory;
    }


    public int getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }

    public void addResources(ResourceType resourceType, int value) {
        resources.merge(resourceType, value, Integer::sum);
    }

    @Override
    public String toString() {
        return String.format("Dices : %s,\nRessouces : %s", Arrays.deepToString(dices), resources);
    }
}
