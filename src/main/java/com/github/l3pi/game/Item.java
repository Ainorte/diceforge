package com.github.l3pi.game;

public class Item {

    private int count;
    private int price;

    public Item(int c,int p){
        this.count = c;
        this.price = p;
    }

    public void decreaseCount(){
        this.count -=1;
    }

    public boolean isItemPurchable(int goldCount){
        if(this.price > goldCount){
            return  false;
        }
        return true;
    }

    public int getCount() {
        return count;
    }
}
