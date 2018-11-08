package com.github.l3pi.game;
import com.github.l3pi.type.FacetType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SanctuaryDice {

    public HashMap<Facet,Item> diceSanctuary;

    public SanctuaryDice(){
        diceSanctuary = new HashMap<>();
    }

    public List<Facet> getAvailableInventory(){
        return diceSanctuary.entrySet().stream().filter( entry -> entry.getValue().getCount() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public void buyDice(Inventory inventory,Facet facet){
        if(diceSanctuary.get(facet).getCount()>0){
            diceSanctuary.get(facet).decreaseCount();
            inventory.forge(facet);
        }
    }

}
