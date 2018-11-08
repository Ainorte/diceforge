package com.github.l3pi.bot;

import com.github.l3pi.game.*;

import java.util.List;
import java.util.Random
public class RandomBot extends Player {
    private Random gen = new Random();
    public RandomBot(String name) {
        super(name);


    }

    public Facet chooseDiceFacet(Game game){
        List<Facet> availableFacets = game.getSanctuaryDice().getAvailableInventory();
        return availableFacets.get(this.gen.nextInt(availableFacets.size())-1);
    }

    @Override
    public int[] forgeMyDice(Game game) {
        return new int[]{this.gen.nextInt(1),this.gen.nextInt(5)};
    }

}
