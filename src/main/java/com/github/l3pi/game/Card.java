package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;

public class Card {
    private int price;
    private CardLocationType locationType;
    private OperationCard operation;
    private boolean isRight;
    private boolean isMiddle;


    Card(int p, CardLocationType l,OperationCard operation,boolean isRight,boolean isMiddle) {
        this.price = p;
        this.locationType = l;
        this.operation = operation;
        this.isRight = isRight;
        this.isMiddle = isMiddle;

    }

    public int getPrice() {
        return price;
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
