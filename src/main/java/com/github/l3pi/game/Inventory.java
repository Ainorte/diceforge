package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class tasked with storing a player's inventory (every associated chunk of data), including, but not limited to,
 * resources, dices.
 */
public class Inventory {

    /** Cette classe représente une inventaire d'un joueur
     *
     * resources c'est le hashmap qui associe un type de ressource et son nombre que posséde le joueur
     * dices c'est une liste de 2 dés
     * extension pour indiqué combien est étendue les capacité de l'inventaire
     * cards c'est la liste des cartes que posséde de le joueur
     *
     * @param gold on initialise un inventaire avec ses golds
     * */

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
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 SOLAR",
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.SOLAR, 1))));
        }};

        ArrayList dice2 = new ArrayList<Facet>() {{
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> game.addGold(player, 1))));
            add(new Facet("1 LUNAR",
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.LUNAR, 1))));
            add(new Facet("2 GLORY", 
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.GLORY, 2))));
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


     /**getMaxRessources retourne le max possible de place dans l'inventaire pour une ressoure donné , si
      * une ressource a étendue son maximum de capacité
      *
      * @param resourceType  c'est le type de ressource
      * */

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

     /** retourne une liste de 2 face qui correspond aux 2 lancé de dé , sont les face du lancé de dé
      *
      *
      * */

    public List<Facet> throwDices() {  // == Faveur Majeur
        return dices.stream().map(Dice::throwDice).collect(Collectors.toList());
    }

    /** forge modifié le dé de l'inventaire , fait appel aux méthode de la classe dé et face de dé
     *
     * */

    public void forge(Facet facetToForge,int choosenDice,int choosenFacet){
        if (facetToForge != null) {
            Dice dice = this.dices.get(choosenDice);
            dice.addFace(facetToForge,choosenFacet);
            this.dices.set(choosenDice,dice);
        }
    }
    /** getDices retourne une liste de 2 element correspondant aux dés
     *
     * */

    public List<Dice> getDices() {
        return new ArrayList<>(dices);
    }

    /** retourne les face en haut du dé , visible au joueur pour les 2 dé
     *
     * */

    public List<Facet> getFaceUp() {
        return dices.stream().map(Dice::getFaceUp).collect(Collectors.toList());
    }



    public List<Facet> getFaceInventory() {
        return new ArrayList<>(faceInventory);
    }
    /**
     * incrémente les extention de ressources
     * */

    public void addExtension(){
        extension++;
    }
    /**getResource
     * retourne le nombre de ressour que le joueur posséde pour un type de ressource donné en argument
     * @param resourceType  le type de ressource que le joueur veut savoir combien il posséde
     *
     * */

    public int getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }

    /** ajoute une valeur de ressource existante dans l'inventaire du joueur
     * @param resourceType  le type de ressource
     * @param value  le nombre de ressource a ajouter
     *
     * */

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
    /**Ajouer une carte dans l'inventaire
     * */

    public void addCard(Card card){
        this.cards.add(card);
    }
    /**Retourne toute les carte disponible dans l'inventaire
     * */

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
            int tmp = (gold > 30 - getHammerGold() ? 30 - getHammerGold() : gold);
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
        return String.format("\tDés :\t\t\t%s,\n\t\t\t\t\t%s,\n\tRessouces :\t\t%s\n\tCartes :\t\t%s", this.dices.get(0).toString(),this.dices.get(1).toString(), resources,cards);
    }
}
