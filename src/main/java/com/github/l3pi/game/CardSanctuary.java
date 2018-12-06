package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

public class CardSanctuary {

     /** c'est la classe qui représente un sanctuaire des cartes
      *
    @param cardSanctuary c'est le hashmap qui associe une carte a son nombre de carte sur un emplacement du sanctuaire

      chaque object carte est identifié par son hash et initialisé au debut tous a 4 nombre d'examplaire
     */
    private HashMap<Card,Integer> cardSanctuary;


    CardSanctuary(){
        cardSanctuary = new HashMap<>();
        this.cardSanctuary.put(new Card(1, ResourceType.LUNAR,CardLocationType.LUNAR1,
            (Game game, Player player)->{
                game.getInventory(player).addHammerCard();
            },"Le Marteau du Forgeron"),4);

        this.cardSanctuary.put(new Card(1,
            ResourceType.LUNAR,CardLocationType.LUNAR1,
            (Game game, Player player)->{
            game.getInventory(player).addExtension();
            },"Le Coffre du Forgeron"),4);


        this.cardSanctuary.put(new Card(3,
            ResourceType.LUNAR,CardLocationType.LUNAR2,
            (Game game, Player player)->{
            //Pas d'effet immediat;
                Inventory inventory = game.getInventory(player);
                List<Dice> dices = inventory.getDices();
                int choosenDice = player.chooseDice(dices);
                Facet faceUp = dices.get(choosenDice).throwDice();

                if(!(faceUp.getName().equals("x3"))){
                    faceUp.getOperation().apply(game, player);
                }

            },"Les Sabots d'Argent",true),4);


        this.cardSanctuary.put(new Card(2,ResourceType.LUNAR,CardLocationType.LUNAR2,
            (Game game, Player player)->{
                //Le joueur recois la combinaison de deux faces de ses adversaires (ici les deux des de son seul adversaire)
                game.getPlayers()
                    .stream().filter(player1 -> !player1.equals(player))
                    .flatMap(player1 -> game.getInventory(player1).throwDices().stream())
                    .forEach(facet ->
                                {facet.getOperation().apply(game,player);});
            },"Les Satyres"),4);

        this.cardSanctuary.put(new Card(4,ResourceType.LUNAR,CardLocationType.LUNAR3,
            (Game game, Player player)->{
                //Pas d'effet cette garde ne donne que 12 Gloire
                game.getInventory(player).addResources(ResourceType.GLORY,12);
            },"Le Passeur"),4);

        this.cardSanctuary.put(new Card(5,ResourceType.LUNAR,CardLocationType.LUNAR3,
            (Game game, Player player)->{
            Inventory inventory = game.getInventory(player);
            Facet facetToForge = new Facet("x3",
                (Game facetGame, Player facetPlayer)->{
                    facetGame.getInventory(facetPlayer).getFaceUp().stream().
                        filter(facet -> !(facet.getName().equals("x3"))).
                        forEach(facet -> {
                            facet.getOperation().apply(facetGame,facetPlayer);
                            facet.getOperation().apply(facetGame,facetPlayer);
                        });

                });
            int[] diceChangeFace = player.forgeMyDice(game, facetToForge);
            inventory.forge(facetToForge,diceChangeFace[0],diceChangeFace[1]);
        },"Le Casque d'invisibilité"),4);


        this.cardSanctuary.put(new Card(6,ResourceType.LUNAR,CardLocationType.MIDDLE,
            (Game game, Player player)->{
            //Le joueur recois 2 faveur divine.
                Inventory inventory = game.getInventory(player);
                List<Facet> facetUp = inventory.throwDices();

                for(int i = 0; i < 2 ; i++) {
                    inventory
                        .getFaceUp()
                        .forEach(facet -> {
                            facet.getOperation().apply(game, player);
                        });
                }
            },"La Pince"),4);

        this.cardSanctuary.put(new Card(5,new ArrayList<ResourceType>(){{add(ResourceType.LUNAR);add(ResourceType.SOLAR);}},CardLocationType.MIDDLE,
            (Game game, Player player)->{
                //Pas d'effet cette garde ne donne que 26 Gloire
                game.getInventory(player).addResources(ResourceType.GLORY,26);
            },"L'hydre",false),4);

        this.cardSanctuary.put(new Card(6,ResourceType.SOLAR,CardLocationType.MIDDLE,(Game game, Player player)->{
            List<Dice> playerDices = game.getInventory(player).getDices();
            Dice choosenDice = playerDices.get(player.chooseDice(playerDices));
            for(int i = 0 ; i < 4 ; i++) {
                choosenDice.throwDice();
                Facet faceUp = choosenDice.getFaceUp();
                if(faceUp.getName().equals("x3")) {
                    continue;
                }
                faceUp.getOperation().apply(game, player);
            }
            },"L'Enigme"),4);


        this.cardSanctuary.put(new Card(1,ResourceType.SOLAR,CardLocationType.SOLAR1,(Game game, Player player)->{
            Inventory inventory = game.getInventory(player);
            if(inventory.getResource(ResourceType.GOLD) > 2 && player.tradeGoldForGlory(game)) {
                inventory.addResources(ResourceType.GOLD, -3);
                inventory.addResources(ResourceType.GLORY, 4);
            }
        },"L'Ancien",true),4);

        this.cardSanctuary.put(new Card(1,ResourceType.SOLAR,CardLocationType.SOLAR1,
            (Game game, Player player)->{
                game.getInventory(player).addResources(ResourceType.GOLD,3);
                game.getInventory(player).addResources(ResourceType.LUNAR,3);
            },"Les Herbes Folles"),4);

        this.cardSanctuary.put(new Card(3,ResourceType.SOLAR,CardLocationType.SOLAR2,
            (Game game,Player player)->{
            ResourceType choosenResource = player.chooseResource(new ArrayList<ResourceType>(Arrays.asList(ResourceType.GOLD,ResourceType.LUNAR,ResourceType.SOLAR)));
            game.getInventory(player).addResources(choosenResource,1);
            },"Les Ailes de la Gardienne",true),4);

        this.cardSanctuary.put(new Card(2,ResourceType.SOLAR,CardLocationType.SOLAR2,
            (Game game, Player player)->{
            //TODO
            },"Le Minotaure"),4);

        this.cardSanctuary.put(new Card(4,ResourceType.SOLAR,CardLocationType.SOLAR3,
            (Game game, Player player)->{
                //Pas d'effet cette garde ne donne que 14 Gloire
                game.getInventory(player).addResources(ResourceType.GLORY,14);
            },"La Meduse"),4);
        
        this.cardSanctuary.put(new Card(5,ResourceType.SOLAR,CardLocationType.SOLAR3,
            (Game game, Player player)->{
            //TODO
            },"Le Miroir Abyssal"),4);
    }


    /**
     * return une liste de carte qui sont encore en stock dans le sanctuaire
     *
     * */

    public List<Card> getAvailableCard() {
        return cardSanctuary.entrySet().stream().filter(entry -> entry.getValue() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * return une liste de carte qui sont achetable depuis un inventaire d'un autre joueur
     *
     * @param inventory c'est l'inventaire d'un joueur .
     *
     * */

    public List<Card> getPurchasableCard(Inventory inventory) {
        return cardSanctuary.entrySet().stream().filter(entry -> (entry.getValue() > 0
            && entry.getKey().getResourceType().stream()
            .map((ResourceType currentRessource) -> entry.getKey().isCardPurchasable(inventory.getResource(currentRessource)))
            .reduce(Boolean.TRUE, (a,b) -> a && b)))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * retourne null si on ne peux plus acheter ou on a pas donné de carte a acheté
     * et met a jour le stock des cartes
     *
     * @param card c'est la carte séléctionné pour achat dans le sanctuaire
     *
     * */


    Card buyCard(Card card) {
        if (card != null) {
            if (cardSanctuary.get(card) > 0) {
                cardSanctuary.put(card, cardSanctuary.get(card) - 1);
                return card;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return cardSanctuary.entrySet().stream()
            .map(facet -> String.format("Nombre de cartes de type \"%s\" disponible: %s", facet.getKey(), facet.getValue()))
            .collect(Collectors.joining("\n"));
    }
}
