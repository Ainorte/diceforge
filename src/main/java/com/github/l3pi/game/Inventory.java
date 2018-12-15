package com.github.l3pi.game;

import com.github.l3pi.sys.Log;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.Log.log;

/**
 * Class tasked with storing a player's inventory (every associated chunk of data), including, but not limited to,
 * resources, dices.
 */
public class Inventory {

    /**
     * Cette classe représente une inventaire d'un joueur
     * <p>
     * resources c'est le hashmap qui associe un type de ressource et son nombre que posséde le joueur
     * dices c'est une liste de 2 dés
     * extension pour indiqué combien est étendue les capacité de l'inventaire
     * cardInventory c'est la liste des cartes que posséde de le joueur
     */

    private final HashMap<ResourceType, Integer> resources;

    private List<Dice> dices;

    private final List<Facet> facetInventory;

    private List<Card> cardInventory;

    private int extension;

    private Hammer hammer;

    public Inventory(HashMap<ResourceType, Integer> resources, List<Dice> dices, List<Facet> facetInventory, List<Card> cardInventory, int extension, Hammer hammer) {

        this.resources = new HashMap<ResourceType, Integer>(resources);
        this.dices = new ArrayList<>(dices);
        this.facetInventory = new ArrayList<Facet>(facetInventory);
        this.cardInventory = new ArrayList<Card>(cardInventory);
        this.extension = extension;
        this.hammer = hammer;
    }

    public Inventory(List<Dice> dices) {
        this(new HashMap<ResourceType, Integer>() {{
            put(ResourceType.GOLD, 0);
            put(ResourceType.LUNAR, 0);
            put(ResourceType.SOLAR, 0);
            put(ResourceType.GLORY, 0);
        }}, dices, new ArrayList<>(), new ArrayList<>(), 0, new Hammer());
    }

    /**
     * @return toutes les ressources
     */
    public HashMap<ResourceType, Integer> getResources() {
        return new HashMap<>(resources);
    }

    /**
     * getResource
     * retourne le nombre de ressour que le joueur posséde pour un type de ressource donné en argument
     *
     * @param resourceType le type de ressource que le joueur veut savoir combien il posséde
     */
    public int getResource(ResourceType resourceType) {
        return resources.get(resourceType);
    }

    /**
     * getMaxRessources retourne le max possible de place dans l'inventaire pour une ressoure donné , si
     * une ressource a étendue son maximum de capacité
     *
     * @param resourceType c'est le type de ressource
     */
    public int getMaxRessources(ResourceType resourceType) {
        switch (resourceType) {
            case GLORY:
                return Integer.MAX_VALUE;
            case GOLD:
                return 12 + (4 * extension);
            case LUNAR:
            case SOLAR:
                return 6 + (3 * extension);
            default:
                return 0;
        }
    }

    /**
     * ajoute une valeur de ressource existante dans l'inventaire du joueur
     *
     * @param resourceType le type de ressource
     * @param value        le nombre de ressource a ajouter
     */
    void addResources(ResourceType resourceType, int value) {
        int currentValue = resources.get(resourceType);
        int max = this.getMaxRessources(resourceType);
        if (value < 0) {
            resources.merge(resourceType, currentValue + value >= 0 ? value : -currentValue, Integer::sum);
        } else if ((currentValue + value) >= 0 && (currentValue + value) < max) {
            resources.merge(resourceType, value, Integer::sum);
        } else {
            resources.merge(resourceType, max - currentValue, Integer::sum);
        }
    }

    /**
     * Function pour Hammergold , permet de rajouter du gold sur la carte de deplacement de cette carte
     *
     * @param player le joueur qui effectue l'action
     * @param gold   le nombre de gold
     */
     public void addGold(Player player, int gold) {
        Hammer hammer = getHammer();
        if (hammer.getCardCount() > 0 && getMaxRessources(ResourceType.GOLD) > getResource(ResourceType.GOLD)) {
            Tuple<Integer, Integer> repartition = player.chooseGoldRepartion(this, gold);
            if (repartition.getY() + repartition.getX() <= gold) {
                addResources(ResourceType.GOLD, repartition.getX());
                hammer.addGold(repartition.getY());
                log(Log.State.GREEN, "\t" + player + Log.State.GREEN + " a mis " + repartition.getX() + " gold dans son inventaire et " + repartition.getY() + " gold dans son marteau");
            }
        } else if (getMaxRessources(ResourceType.GOLD) <= getResource(ResourceType.GOLD) && hammer.getCardCount() > 0) {
            hammer.addGold(gold);
            log(Log.State.GREEN, "\t" + player + Log.State.GREEN + " a mis " + gold + " gold dans son marteau");
        } else {
            addResources(ResourceType.GOLD, gold);
            log(Log.State.GREEN, "\t" + player + Log.State.GREEN + " a mis " + gold + " gold dans son inventaire");
        }
    }


    /**
     * getDices retourne une liste de 2 element correspondant aux dés
     */
    public List<Dice> getDices() {
        return new ArrayList<>(dices);
    }

    /**
     * retourne les face en haut du dé , visible au joueur pour les 2 dé
     */
    public List<Facet> getFaceUp() {
        return dices.stream().map(Dice::getFaceUp).collect(Collectors.toList());
    }

    /**
     * retourne une liste de 2 face qui correspond aux 2 lancé de dé , sont les face du lancé de dé
     */
    List<Facet> throwDices() {  // == Faveur Majeur
        return dices.stream().map(Dice::throwDice).collect(Collectors.toList());
    }

    /**
     * forge modifié le dé de l'inventaire , fait appel aux méthode de la classe dé et face de dé
     */
    void forge(Facet facetToForge, int choosenDice, int choosenFacet) {
        Dice dice = this.dices.get(choosenDice);
        dice.forgeFacet(facetToForge, choosenFacet);
    }

    /**
     * @return liste des facets qui ne sont pas actuellement forgées et qui se trouve dans l'inventaire
     */
    public List<Facet> getFacetInventory() {
        return new ArrayList<>(facetInventory);
    }


    /**
     * Ajouer une carte dans l'inventaire
     */
    void addCard(Card card) {
        this.cardInventory.add(card);
    }

    /**
     * Retourne toute les carte disponible dans l'inventaire
     */
    public List<Card> getCardInventory() {
        return new ArrayList<Card>(cardInventory);
    }


    /**
     * incrémente les extention de ressources
     */
    void addExtension() {
        extension++;
    }


    public Hammer getHammer() {
        return hammer;
    }


    @Override
    public String toString() {
        return String.format("\tDés\t\t\t\t:\t%s,\n\t\t\t\t\t\t%s\n\tRessouces\t\t:\t%s\n\tCartes\t\t\t:\t%s\n\tExtensions\t\t:\t%d cartes\n\tMarteaux\t\t:\t%s", this.dices.get(0).toString(), this.dices.get(1).toString(), resources, cardInventory, extension, hammer);
    }
}
