package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private int price;
    private CardLocationType locationType;
    private OperationCard operation;
    private boolean isRight;
    private boolean isMiddle;
    private List<ResourceType> resourceType;


    Card(int p,List<ResourceType> resourceType, CardLocationType location,OperationCard operation,boolean isRight,boolean isMiddle) {
        this.price = p;
        this.resourceType = resourceType;
        this.locationType = location;
        this.operation = operation;
        this.isRight = isRight;
        this.isMiddle = isMiddle;

    }

    Card(int p,ResourceType resourceType, CardLocationType location,OperationCard operation,boolean isRight,boolean isMiddle) {
        this(p,new ArrayList<ResourceType>(){{add(resourceType);}},location,operation,isRight,isMiddle);
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

    public OperationCard getOperation() {
        return this.operation;
    }

    public boolean isRight(){
        return this.isRight;
    }

    public boolean isCardPurshable(int money){
        return this.price <= money;
    }
}
