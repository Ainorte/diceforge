package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Card {

    private int price;
    private List<ResourceType> resourceType;

    private CardLocationType locationType;
    private Operation operation;

    private String name;
    private boolean recurrent;

    private String formattedEffect;

    /**
     Cette classe représente une carte dans le jeu

     @param location la location de la carte sur le sanctuaire des cartes
     @param name c'est le nom de la carte
     @param operation c'est le lambda expression qui applique les effects de la carte
     @param p c'est le prix de la carte
     @param recurrent pour indiquer si les effets de la carte sont déclanché a chaque début de tour
     @param resourceType c'est le type  de ressource pour necessaire pour acheter cette carte

     */

    public Card(int p, List<ResourceType> resourceType, CardLocationType location, Operation operation, String name, boolean recurrent, String formattedEffect) {
        this.price = p;
        this.resourceType = new ArrayList<>(resourceType);
        this.locationType = location;
        this.operation = operation;
        this.recurrent = recurrent;
        this.name = name;
        this.formattedEffect = formattedEffect;

    }

    public Card(int p,ResourceType resourceType, CardLocationType location,Operation operation, String name) {
        this(p, new ArrayList<ResourceType>() {{
            add(resourceType);
        }}, location, operation, name, false, "%s a effectué l'effet");
    }

    public Card(int p, ResourceType resourceType, CardLocationType location, Operation operation, String name, String formattedEffect) {
        this(p, new ArrayList<ResourceType>() {{
            add(resourceType);
        }}, location, operation, name, false, formattedEffect);
    }


    public Card(int p, ResourceType resourceType, CardLocationType location, Operation operation, String name, boolean recurrent, String formattedEffect) {
        this(p, new ArrayList<ResourceType>() {{
            add(resourceType);
        }}, location, operation, name, recurrent, formattedEffect);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    public List<ResourceType> getResourceType() {
        return new ArrayList<>(resourceType);
    }

    public CardLocationType getLocation() {
        return locationType;
    }

    public Operation getOperation() {
        return this.operation;
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    /**
     * @param resources Inventaire de ressources du joueur
     * @return un boolean , true si achetable, false si non
     */
    public boolean isPurchasable(HashMap<ResourceType, Integer> resources) {
        boolean result = true;
        for (int i = 0; i < resourceType.size() && result; i++) {
            result = resources.get(resourceType.get(i)) >= price;
        }
        return result;
    }

    public String displayEffect(Player player) {
        return String.format(this.formattedEffect, player);
    }


    @Override
    public String toString() {
        return name;
    }
}
