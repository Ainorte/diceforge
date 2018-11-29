package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.List;
import java.util.Random;

public class RandomBot extends Player {
    private Random gen;
    private CardLocationType location = null;

    public RandomBot(String name) {
        super(name);
        gen = new Random();
    }

    @Override
    public Facet chooseDiceFacet(Game game) {
        List<Facet> purchasableFacets = game.getDiceSanctuary().getPurchasableInventory(game.getInventory(this).getResource(ResourceType.GOLD));

        if (purchasableFacets.size() > 0) {
            return purchasableFacets.get(this.gen.nextInt(purchasableFacets.size()));
        }
        return null;
    }

    @Override
    public Card chooseCard(Game game){
        List<Card> purchasableCards = game.getCardSanctuary().getPurchasableCard(game.getInventory(this));
        System.out.println("choix des cartes achetables :" + purchasableCards);
        if (purchasableCards.size() > 0) {
            return purchasableCards.get(this.gen.nextInt(purchasableCards.size()));
        }
        return null;
    }

    @Override
    public int chooseAction(Game game){
        return this.gen.nextInt(2);
    }

    @Override
    public void move(CardLocationType location){
        this.location = location;
    }

    @Override
    public int chooseDice(List<Dice> dices){
        return this.gen.nextInt(dices.size());
    };




    @Override
    public int[] forgeMyDice(Game game,Facet facet) {

        return new int[]{this.gen.nextInt(1),this.gen.nextInt(5)};
    }

}
