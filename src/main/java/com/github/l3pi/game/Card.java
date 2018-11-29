package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private int price;
    private List<ResourceType> resourceType;

    private CardLocationType locationType;
    private Operation operation;

    private String name;
    private boolean recurrent;

    public Card(int p,List<ResourceType> resourceType, CardLocationType location,Operation operation,String name,boolean recurrent) {
        this.price = p;
        this.resourceType = resourceType;
        this.locationType = location;
        this.operation = operation;
        this.recurrent = recurrent;
        this.name = name;

    }

    public Card(int p,ResourceType resourceType, CardLocationType location,Operation operation, String name) {
        this(p,new ArrayList<ResourceType>(){{add(resourceType);}},location,operation,name,false);
    }

    public Card(int p,ResourceType resourceType, CardLocationType location,Operation operation, String name,boolean recurrent) {
        this(p,new ArrayList<ResourceType>(){{add(resourceType);}},location,operation,name,recurrent);
    }

    public boolean isRecurrent() {
        return recurrent;
    }

    public int getPrice() {
        return price;
    }

    public List<ResourceType> getResourceType() {
        return resourceType;
    }

    public CardLocationType getLocationType() {
        return locationType;
    }

    public Operation getOperation() {
        return this.operation;
    }


    public boolean isCardPurchasable(int money){
        return this.price <= money;
    }


    @Override
    public String toString() {
        return name;
    }
}
