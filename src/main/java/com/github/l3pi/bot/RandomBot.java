package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.ResourceType;

import java.util.List;
import java.util.Random;

public class RandomBot extends Player {
    private Random gen;
    public RandomBot(String name) {
        super(name);
        gen = new Random();
    }

    public Facet chooseDiceFacet(Game game){
        List<Facet> purchasableFacets = game.getSanctuaryDice().getPurchasableInventory(game.getInventory(this).getResource(ResourceType.GOLD));
        if(purchasableFacets.size() > 0) {
            if(purchasableFacets.size() == 1){
                return purchasableFacets.get(0);
            }
            return purchasableFacets.get(this.gen.nextInt(purchasableFacets.size() - 1));
        }
        return null;
    }

    @Override
    public int[] forgeMyDice(Game game) {
        return new int[]{this.gen.nextInt(1),this.gen.nextInt(5)};
    }

}
