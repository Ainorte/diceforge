package com.github.l3pi.factory;

import com.github.l3pi.game.Facet;
import com.github.l3pi.type.FacetType;

public class DiceFactory {
    private static DiceFactory ourInstance = new DiceFactory();

    public static DiceFactory getInstance() {
        return ourInstance;
    }

    private DiceFactory() {
    }

    public Facet[][] getInitialDices() {
        return new Facet[][]{
            new Facet[]{
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.SOLAR,1)
            },
            new Facet[]{
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.GOLD,1),
                new Facet(FacetType.LUNAR,1),
                new Facet(FacetType.GLORY,2),
            }
        };
    }
}
