package com.github.l3pi.game;

/**
 * Data class made to be tied with the Sanctuary, to allow
 * storing of every sanctuary-related metadata associated with a given item.
 *
 * @see DiceSanctuary
 */
class Item {

    private int count;
    private int price;

    Item(int c, int p) {
        this.count = c;
        this.price = p;
    }

    void decreaseCount() {
        this.count -= 1;
    }

    boolean isItemPurchable(int goldCount) {
        return this.price <= goldCount;
    }

    int getCount() {
        return count;
    }

    int getPrice() {
        return price;
    }
}
