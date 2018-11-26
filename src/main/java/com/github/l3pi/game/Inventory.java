package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class tasked with storing a player's inventory (every associated chunk of data), including, but not limited to,
 * resources, dices.
 */
public class Inventory {
    private final List<Facet> faceInventory;
    private final HashMap<ResourceType, Integer> resources;
    private List<Dice> dices;
    private int extension;
    private List<Card> cards;

    public Inventory(int gold) {
        ArrayList dice1 = new ArrayList<Facet>() {{
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Solar", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.SOLAR, 1);
            })));
        }};

        ArrayList dice2 = new ArrayList<Facet>() {{
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Gold", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 Lunar", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.LUNAR, 1);
            })));
            add(new Facet("2 Glory", 1, ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GLORY, 2);
            })));
        }};

        this.dices = new ArrayList<Dice>(){{
            add(new Dice(dice1));
            add(new Dice(dice2));
        }};

        this.resources = new HashMap<ResourceType,Integer>(){{
            put(ResourceType.GOLD, gold);
            put(ResourceType.LUNAR, 0);
            put(ResourceType.SOLAR, 0);
            put(ResourceType.GLORY, 0);
        }};

        this.faceInventory = new ArrayList<Facet>();
        this.extension = 0;
        this.cards = new ArrayList<Card>() {
        };
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

    public List<Facet> throwDices() {  // == Faveur Majeur
        return dices.stream().map(Dice::throwDice).collect(Collectors.toList());
    }

    public void forge(Facet facetToForge,int choosenDice,int choosenFacet){
        if (facetToForge != null) {
            Dice dice = this.dices.get(choosenDice);
            dice.addFace(facetToForge,choosenFacet);
            this.dices.set(choosenDice,dice);
        }
    }


    public List<Dice> getDices() {
        return dices;
    }

    public Facet[] getFaceUp() {
        return new Facet[]{this.dices.get(0).getFaceUp(),this.dices.get(1).getFaceUp()};
    }

    public List<Facet> getFaceInventory() {
        return faceInventory;
    }

    public void addExtension(){
        extension++;
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

    public void addCard(Card card){
        this.cards.add(card);
    }

    @Override
    public String toString() {
        return String.format("Dices : %s,\nRessouces : %s\nCards : %s", this.dices.toString(), resources,cards);
    }
}
