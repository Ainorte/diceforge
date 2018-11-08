package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import java.util.List;
import java.util.Random;

public class RandomBot extends Player {
    private Random gen;
    public RandomBot(String name) {
        super(name);
        gen = new Random();
    }

    public Facet chooseDiceFacet(Game game){
        List<Facet> availableFacets = game.getSanctuaryDice().getAvailableInventory();
        if(availableFacets.size() > 0) {
            return availableFacets.get(this.gen.nextInt(availableFacets.size()) - 1);
        }
        return null;
    }

    @Override
    public int[] forgeMyDice(Game game) {
        return new int[]{this.gen.nextInt(1),this.gen.nextInt(5)};
    }

}
