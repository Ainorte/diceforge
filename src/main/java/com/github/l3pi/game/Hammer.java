package com.github.l3pi.game;

public class Hammer {
    private int gold;
    private int card;

    public Hammer() {
        gold = 0;
        card = 0;
    }

    public int getCardCount() {
        return card - gold / 30;
    }

    public int getGold() {
        return gold - ((card - getCardCount()) * 30);
    }

    public int getAvailableSpace() {
        return card * 30 - gold;
    }

    /**
     * @param gold nombre de glod à ajouter
     * @return nombre de GLORY gagné
     */
    int addGold(int gold) {
        int glory = 0;
        while (gold > 0) {
            int tmp = (gold > 30 - getGold() ? 30 - getGold() : gold);
            gold -= tmp;
            glory += addGoldOneCard(tmp);
        }
        return glory;
    }

    private int addGoldOneCard(int gold) {
        if (getCardCount() == 0) {
            return 0;
        }
        int glory = 0;
        int oldGold = getGold();
        this.gold += gold;

        if (oldGold < 15 && oldGold + gold >= 15) {
            glory += 10;
        }
        if (oldGold + gold >= 30) {
            glory += 15;
        }
        return glory;
    }

    void addCard() {
        card++;
    }

    @Override
    public String toString() {
        return String.format("%d cartes utilisées, %d cartes actives, %d gold stockés sur la dernière carte", card - getCardCount(), getCardCount(), getGold());
    }
}
