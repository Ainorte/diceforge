package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardSanctuary {

    private HashMap<Card,Integer> cardSanctuary;

    CardSanctuary(){
        this.cardSanctuary.put(new Card(1,CardLocationType.LUNAR1,(Game game, Inventory inventory)->{},true,false),4);
        this.cardSanctuary.put(new Card(1,CardLocationType.LUNAR1,(Game game, Inventory inventory)->{},false,false),4);

        this.cardSanctuary.put(new Card(3,CardLocationType.LUNAR2,(Game game, Inventory inventory)->{},true,false),4);
        this.cardSanctuary.put(new Card(2,CardLocationType.LUNAR2,(Game game, Inventory inventory)->{},false,false),4);

        this.cardSanctuary.put(new Card(4,CardLocationType.LUNAR3,(Game game, Inventory inventory)->{},true,false),4);
        this.cardSanctuary.put(new Card(5,CardLocationType.LUNAR3,(Game game, Inventory inventory)->{},false,false),4);

        this.cardSanctuary.put(new Card(6,CardLocationType.MIDDLE,(Game game, Inventory inventory)->{},true,false),4);
        this.cardSanctuary.put(new Card(5,CardLocationType.MIDDLE,(Game game, Inventory inventory)->{},false,true),4);
        this.cardSanctuary.put(new Card(6,CardLocationType.MIDDLE,(Game game, Inventory inventory)->{},false,false),4);


        this.cardSanctuary.put(new Card(1,CardLocationType.SOLAR1,(Game game, Inventory inventory)->{},true,false),4);
        this.cardSanctuary.put(new Card(1,CardLocationType.SOLAR1,(Game game, Inventory inventory)->{},false,false),4);

        this.cardSanctuary.put(new Card(3,CardLocationType.SOLAR2,(Game game, Inventory inventory)->{},true,false),4);
        this.cardSanctuary.put(new Card(2,CardLocationType.SOLAR2,(Game game, Inventory inventory)->{},false,false),4);

        this.cardSanctuary.put(new Card(4,CardLocationType.SOLAR3,(Game game, Inventory inventory)->{},true,false),4);
        this.cardSanctuary.put(new Card(5,CardLocationType.SOLAR3,(Game game, Inventory inventory)->{},false,false),4);
    }


    public List<Card> getAvailableCard() {
        return cardSanctuary.entrySet().stream().filter(entry -> entry.getValue() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public List<Card> getPurchasableCard(int gold) {
        return cardSanctuary.entrySet().stream().filter(entry -> (entry.getValue() > 0 && entry.getKey().isCardPurshable(gold)))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    Card buyFacet(Card card) {
        if (card != null) {
            if (cardSanctuary.get(card) > 0) {
                cardSanctuary.put(card, cardSanctuary.get(card) - 1);
                return card;
            }

        }
        return null;
    }

    int getPriceForFacet(Card card) {
        return cardSanctuary.containsKey(card) ? cardSanctuary.get(card) : 0;
    }

}
