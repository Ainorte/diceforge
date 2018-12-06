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

    private int hammerGold;

    private int hammerCard;

    public Inventory(int gold) {
        ArrayList dice1 = new ArrayList<Facet>() {{
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 SOLAR",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.SOLAR, 1);
            })));
        }};

        ArrayList dice2 = new ArrayList<Facet>() {{
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GOLD, 1);
            })));
            add(new Facet("1 LUNAR",
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.LUNAR, 1);
            })));
            add(new Facet("2 GLORY", 
                ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GLORY, 2);
            })));
        }};

        this.dices = new ArrayList<Dice>(){{
            add(new Dice(dice1));
            add(new Dice(dice2));
        }};

        this.resources = new HashMap<ResourceType,Integer>(){{
            put(ResourceType.GOLD, 0);
            put(ResourceType.LUNAR, 0);
            put(ResourceType.SOLAR, 0);
            put(ResourceType.GLORY, 0);
        }};

        this.faceInventory = new ArrayList<Facet>();
        this.extension = 0;
        this.cards = new ArrayList<Card>() {
        };

        addResources(ResourceType.GOLD,gold);

        this.hammerCard = 0;
        this.hammerGold = 0;
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
        return new ArrayList<>(dices);
    }

    public List<Facet> getFaceUp() {
        return dices.stream().map(Dice::getFaceUp).collect(Collectors.toList());
    }

    public List<Facet> getFaceInventory() {
        return new ArrayList<>(faceInventory);
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

    public List<Card> getCards(){
     return new ArrayList<Card>(this.cards);
    }

    public int getActiveHammerCardCount(){
        return hammerCard - hammerGold/30;
    }
    public int getHammerGold(){
        return hammerGold - ((hammerCard - getActiveHammerCardCount())*30);
    }

    /*
     *@return nb of GOLRY win
     */
    public int addGoldHammer(int gold){
        int glory = 0;
        while (gold > 0){
            int tmp = (gold > 30 ? 30 : gold) - getHammerGold();
            gold -= tmp;
            glory += addGoldHammerOneCard(tmp);
        }
        addResources(ResourceType.GLORY,glory);
        return glory;
    }

    private int addGoldHammerOneCard(int gold){
        if(getActiveHammerCardCount() == 0){
            return 0;
        }
        int glory = 0;
        int oldGold = getHammerGold();
        hammerGold+= gold;

        if(oldGold < 15 && oldGold + gold > 15){
            glory += 10;
        }
        if(oldGold + gold >= 30){
            glory += 15;
        }
        return glory;
    }

    public void addHammerCard(){
        hammerCard++;
    }

    @Override
    public String toString() {
        return String.format("Dices :\t%s,\n\t\t%s,\nResources :\t%s\nCards :\t%s", this.dices.get(0).toString(),this.dices.get(1).toString(), resources,cards);
    }
}
