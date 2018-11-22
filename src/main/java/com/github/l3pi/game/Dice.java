package com.github.l3pi.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
import static com.github.l3pi.sys.LogDAO.log;

public class Dice {

    private List<Facet> DiceFacet;
    private Random random;

    Dice(List<Facet> facets){
        this.DiceFacet = facets;

    }

    public Facet throwDice() {  // == Faveur Majeur
        Facet facet;
        int randIndex = random.nextInt(DiceFacet.size());

        facet = this.DiceFacet.get(randIndex);
        Collections.shuffle(this.DiceFacet);

        return facet;
    }

    public Facet getFaceUp(){
        return this.DiceFacet.get(0);
    }


}
