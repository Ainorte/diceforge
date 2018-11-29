package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardSanctuary {

    private HashMap<Card,Integer> cardSanctuary;

    CardSanctuary(){
        cardSanctuary = new HashMap<>();
        this.cardSanctuary.put(new Card(1, ResourceType.LUNAR,CardLocationType.LUNAR1,(Game game, Player player)->{},"Le Marteau du Forgeron"),4);

        this.cardSanctuary.put(new Card(1,
            ResourceType.LUNAR,CardLocationType.LUNAR1,
            (Game game, Player player)->{
            game.getInventory(player).addExtension();
            },"Le Coffre du Forgeron"),4);


        this.cardSanctuary.put(new Card(3,
            ResourceType.LUNAR,CardLocationType.LUNAR2,
            (Game game, Player player)->{
            //Pas d'effet immediat;
                game.getInventory(player)
                    .getDices()
                    .get(player.chooseDice(game.getInventory(player).getDices()))
                    .throwDice()
                    .getOperation().
                    apply(game,player);
            },"Les Sabots d'Argent",true),4);


        this.cardSanctuary.put(new Card(2,ResourceType.LUNAR,CardLocationType.LUNAR2,
            (Game game, Player player)->{
                game.getPlayers().stream().filter(player1 -> !player1.equals(player)).flatMap(player1 -> game.getInventory(player1).throwDices().stream()).forEach(facet -> {facet.getOperation().apply(this,player);});
        },"Les Satyres"),4);

        this.cardSanctuary.put(new Card(4,ResourceType.LUNAR,CardLocationType.LUNAR3,(Game game, Player player)->{},"Le Passeur"),4);
        this.cardSanctuary.put(new Card(5,ResourceType.LUNAR,CardLocationType.LUNAR3,(Game game, Player player)->{},"Le Casque d'invisibilité"),4);

        this.cardSanctuary.put(new Card(6,ResourceType.LUNAR,CardLocationType.MIDDLE,(Game game, Player player)->{},"La Pince"),4);
        this.cardSanctuary.put(new Card(5,new ArrayList<ResourceType>(){{add(ResourceType.LUNAR);add(ResourceType.SOLAR);}},CardLocationType.MIDDLE,(Game game, Player player)->{},"L'hydre",false),4);
        this.cardSanctuary.put(new Card(6,ResourceType.SOLAR,CardLocationType.MIDDLE,(Game game, Player player)->{},"L'Enigme"),4);


        this.cardSanctuary.put(new Card(1,ResourceType.SOLAR,CardLocationType.SOLAR1,(Game game, Player player)->{},"L'Ancien",true),4);
        this.cardSanctuary.put(new Card(1,ResourceType.SOLAR,CardLocationType.SOLAR1,(Game game, Player player)->{},"Les Herbes Folles"),4);

        this.cardSanctuary.put(new Card(3,ResourceType.SOLAR,CardLocationType.SOLAR2,(Game game,Player player)->{},"Les Ailes de la Gardienne",true),4);
        this.cardSanctuary.put(new Card(2,ResourceType.SOLAR,CardLocationType.SOLAR2,(Game game, Player player)->{},"Le Minotaure"),4);

        this.cardSanctuary.put(new Card(4,ResourceType.SOLAR,CardLocationType.SOLAR3,(Game game, Player player)->{},"La Meduse"),4);
        this.cardSanctuary.put(new Card(5,ResourceType.SOLAR,CardLocationType.SOLAR3,(Game game, Player player)->{},"Le Miroir Abyssal"),4);
    }


    public List<Card> getAvailableCard() {
        return cardSanctuary.entrySet().stream().filter(entry -> entry.getValue() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    public List<Card> getPurchasableCard(Inventory inventory) {
        return cardSanctuary.entrySet().stream().filter(entry -> (entry.getValue() > 0
            && entry.getKey().getResourceType().stream()
            .map((ResourceType currentRessource) -> entry.getKey().isCardPurchasable(inventory.getResource(currentRessource)))
            .reduce(Boolean.TRUE, (a,b) -> a && b)))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    Card buyCard(Card card) {
        if (card != null) {
            if (cardSanctuary.get(card) > 0) {
                cardSanctuary.put(card, cardSanctuary.get(card) - 1);
                return card;
            }
        }
        return null;
    }
}
