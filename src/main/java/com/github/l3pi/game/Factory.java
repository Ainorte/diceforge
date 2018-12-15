package com.github.l3pi.game;

import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

public class Factory {
    public static DiceSanctuary generateDiceSanctuary(List<Player> players) {
        int numberOfFacets = players.size() == 2 ? 2 : 4;

        HashMap<Facet, Integer> diceSanctuary = new HashMap<>();

        diceSanctuary.put(
            new Facet("3 GOLD", 2,
                ((Game game, Player player) -> game.getInventory(player).addGold(player, 3))),
            numberOfFacets);


        diceSanctuary.put(new Facet("1 LUNAR", 2,
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.LUNAR, 1))),
            numberOfFacets);





        diceSanctuary.put(new Facet("4 GOLD", 3,
                ((Game game, Player player) -> game.getInventory(player).addGold(player, 4))),
            numberOfFacets);


        diceSanctuary.put(new Facet("1 SOLAR", 3,
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.SOLAR, 1))),
            numberOfFacets);


        List<Facet> pool4 = new ArrayList<>();

        pool4.add(new Facet("6 GOLD", 4,
                ((Game game, Player player) -> game.getInventory(player).addGold(player, 6))));

        pool4.add(new Facet("1 GLORY + SOLAR", 4,
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GLORY, 1);
                game.getInventory(player).addResources(ResourceType.SOLAR, 1);
            })));
        pool4.add(new Facet("2 GOLD + 1 LUNAR", 4,
            ((Game game, Player player) -> {
                game.getInventory(player).addGold(player, 2);
                game.getInventory(player).addResources(ResourceType.LUNAR, 1);
            })));
        pool4.add(new Facet("1 GOLD / LUNAR / SOLAR", 4,
                ((Game game, Player player) -> {
                    ResourceType choosenResource = player.chooseResource(new ArrayList<ResourceType>(Arrays.asList(ResourceType.GOLD, ResourceType.LUNAR, ResourceType.SOLAR)));
                    if (choosenResource == ResourceType.GOLD) {
                        game.getInventory(player).addGold(player, 1);
                    } else {
                        game.getInventory(player).addResources(choosenResource, 1);
                    }
                })));


        for(int i = 0;i<numberOfFacets;i++){
            Collections.shuffle(pool4);
            diceSanctuary.put(pool4.remove(0),1);
        }


        diceSanctuary.put(new Facet("3 GOLD / 2 GLORY", 5,
                ((Game game, Player player) -> {
                    ResourceType choosenResource = player.chooseResource(new ArrayList<ResourceType>(Arrays.asList(ResourceType.GOLD, ResourceType.GLORY)));
                    if (choosenResource == ResourceType.GOLD) {
                        game.getInventory(player).addGold(player, 3);
                    } else {
                        game.getInventory(player).addResources(choosenResource, 2);
                    }
                })),
            numberOfFacets);




        diceSanctuary.put(new Facet("2 LUNAR", 6,
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.LUNAR, 2))),
            numberOfFacets);





        diceSanctuary.put(new Facet("2 SOLAR", 8,
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.SOLAR, 2))),
            numberOfFacets);



        diceSanctuary.put(new Facet("3 GLORY", 8,
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.GLORY, 3))),
            numberOfFacets);



        List<Facet> pool12= new ArrayList<>();
        pool12.add(new Facet("4 GLORY", 12,
                ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.GLORY, 4))));
        pool12.add(new Facet("2 GLORY + LUNAR", 12,
            ((Game game, Player player) -> {
                game.getInventory(player).addResources(ResourceType.GLORY, 2);
                game.getInventory(player).addResources(ResourceType.LUNAR, 2);
            })));

        pool12.add(new Facet("1 GOLD + LUNAR + GLORY + SOLAR", 12,
            ((Game game, Player player) -> {
                game.getInventory(player).addGold(player, 1);
                game.getInventory(player).addResources(ResourceType.LUNAR, 1);
                game.getInventory(player).addResources(ResourceType.GLORY, 1);
                game.getInventory(player).addResources(ResourceType.SOLAR, 1);
            })));
        pool12.add(new Facet("2 GOLD / LUNAR / SOLAR", 12,
                ((Game game, Player player) -> {
                    ResourceType choosenResource = player.chooseResource(new ArrayList<ResourceType>(Arrays.asList(ResourceType.GOLD, ResourceType.LUNAR, ResourceType.SOLAR)));
                    if (choosenResource == ResourceType.GOLD) {
                        game.getInventory(player).addGold(player, 2);
                    } else {
                        game.getInventory(player).addResources(choosenResource, 2);
                    }
                })));

        for(int i = 0;i<numberOfFacets;i++){
            Collections.shuffle(pool12);
            diceSanctuary.put(pool12.remove(0),1);
        }

        return new DiceSanctuary(diceSanctuary);
    }

    public static CardSanctuary generateCardSanctuary(List<Player> players) {
        int numOfCards = players.size();
        HashMap<Card, Integer> cardSanctuary = new HashMap<>();

        cardSanctuary.put(new Card(1, ResourceType.LUNAR, CardLocationType.LUNAR1,
            (Game game, Player player) -> game.getInventory(player).getHammer().addCard(),
            "Le Marteau du Forgeron",
            false,
            "%s a retourne Le Marteau du Forgeron et peut y ajouter de l'or"), numOfCards);

        cardSanctuary.put(new Card(1,
            ResourceType.LUNAR, CardLocationType.LUNAR1,
            (Game game, Player player) -> game.getInventory(player).addExtension(),
            "Le Coffre du Forgeron",
            false,
            "%s a reçu une extension d'inventaire"), numOfCards);


        cardSanctuary.put(new Card(3,
            ResourceType.LUNAR, CardLocationType.LUNAR2,
            (Game game, Player player) -> {
                //Pas d'effet immediat
                Inventory inventory = game.getInventory(player);
                List<Dice> dices = inventory.getDices();
                int choosenDice = player.chooseDice(dices);
                Facet faceUp = dices.get(choosenDice).throwDice();

                if (!(faceUp.getName().equals("x3"))) {
                    faceUp.getOperation().apply(game, player);
                }

            },
            "Les Sabots d'Argent",
            true,
            "%s reçevra une faveur mineure au début de ses tours"), numOfCards);


        cardSanctuary.put(new Card(2, ResourceType.LUNAR, CardLocationType.LUNAR2,
            (Game game, Player player) -> {
                //Le joueur recois la combinaison de deux faces de ses adversaires
                List<Facet> facets = game.getPlayers().stream()
                    .filter(p -> !p.equals(player))
                    .flatMap(p -> game.getInventory(p).throwDices().stream())
                    .collect(Collectors.toList());
                Facet first = player.chooseFacetToApply(facets,game);
                facets.remove(first);
                Facet second = player.chooseFacetToApply(facets,game);
                if (null != first && first.getName().equals("x3")) {
                    second.getOperation().apply(game,player);
                    second.getOperation().apply(game,player);
                    second.getOperation().apply(game,player);
                } else if (null != second && second.getName().equals("x3")) {
                    first.getOperation().apply(game,player);
                    first.getOperation().apply(game,player);
                    first.getOperation().apply(game,player);
                } else {
                    if (null != first) {
                        first.getOperation().apply(game, player);
                    }
                    if (null != second) {
                        second.getOperation().apply(game, player);
                    }
                }


            },
            "Les Satyres",
            "%s gagné les ressources d'un lancé de dés de son adversaire"), numOfCards);

        //Pas d'effet cette garde ne donne que 12 Gloire
        cardSanctuary.put(new Card(4, ResourceType.LUNAR, CardLocationType.LUNAR3,
            (Game game, Player player) -> game.getInventory(player).addResources(ResourceType.GLORY, 12),
            "Le Passeur",
            "%s a gagné 12 Gloire"), numOfCards);


        cardSanctuary.put(new Card(5, ResourceType.LUNAR, CardLocationType.LUNAR3,
            (Game game, Player player) -> {
                Inventory inventory = game.getInventory(player);
                Facet facetToForge = new Facet("x3",
                    (Game facetGame, Player facetPlayer) -> facetGame.getInventory(facetPlayer).getFaceUp().stream().
                        filter(facet -> !(facet.getName().equals("x3"))).
                        forEach(facet -> {
                            facet.getOperation().apply(facetGame, facetPlayer);
                            facet.getOperation().apply(facetGame, facetPlayer);
                        })
                );
                int[] diceChangeFace = player.forgeDice(game, facetToForge);
                inventory.forge(facetToForge, diceChangeFace[0], diceChangeFace[1]);
            },
            "Le Casque d'invisibilité",
            "%s a reçu la face de dé x3 et l'a forgé"), numOfCards);


        cardSanctuary.put(new Card(6, ResourceType.LUNAR, CardLocationType.MIDDLE,
            (Game game, Player player) -> {
                //Le joueur recois 2 faveur divine.
                Inventory inventory = game.getInventory(player);
                List<Facet> facetUp = inventory.throwDices();

                for (int i = 0; i < 2; i++) {
                    facetUp
                        .forEach(facet -> facet.getOperation().apply(game, player));
                }
            },
            "La Pince",
            "%s a reçu 2 faveur majeur"), numOfCards);

        cardSanctuary.put(new Card(5, new ArrayList<ResourceType>() {{
            add(ResourceType.LUNAR);
            add(ResourceType.SOLAR);
        }}, CardLocationType.MIDDLE,
            //Pas d'effet cette garde ne donne que 26 Gloire
            (Game game, Player player) -> game.getInventory(player).addResources(ResourceType.GLORY, 26),
            "L'Hydre",
            false,
            "%s a reçu 26 gloire"), numOfCards);

        cardSanctuary.put(new Card(6, ResourceType.SOLAR, CardLocationType.MIDDLE, (Game game, Player player) -> {
            List<Dice> playerDices = game.getInventory(player).getDices();
            Dice choosenDice = playerDices.get(player.chooseDice(playerDices));
            for (int i = 0; i < 4; i++) {
                choosenDice.throwDice();
                Facet faceUp = choosenDice.getFaceUp();
                if (faceUp.getName().equals("x3")) {
                    continue;
                }
                faceUp.getOperation().apply(game, player);
            }
        },
            "L'Enigme",
            "%s a reçu 4 faveur mineur"), numOfCards);


        cardSanctuary.put(new Card(1, ResourceType.SOLAR, CardLocationType.SOLAR1, (Game game, Player player) -> {
            Inventory inventory = game.getInventory(player);
            if (inventory.getResource(ResourceType.GOLD) > 2 && player.tradeGoldForGlory(game)) {
                inventory.addResources(ResourceType.GOLD, -3);
                inventory.addResources(ResourceType.GLORY, numOfCards);
            }
        },
            "L'Ancien",
            true,
            "%s a gagné la possibilité de changer 3 Or en 4 Gloire au début de ses prochain tours"), 4);

        cardSanctuary.put(new Card(1, ResourceType.SOLAR, CardLocationType.SOLAR1,
            (Game game, Player player) -> {
                game.getInventory(player).addGold(player, 3);
                game.getInventory(player).addResources(ResourceType.LUNAR, 3);
            },
            "Les Herbes Folles",
            false,
            "%s a gagné 3 Or et 3 Lunaire"), numOfCards);

        cardSanctuary.put(new Card(3, ResourceType.SOLAR, CardLocationType.SOLAR2,
            (Game game, Player player) -> {
                ResourceType choosenResource = player.chooseResource(new ArrayList<ResourceType>(Arrays.asList(ResourceType.GOLD, ResourceType.LUNAR, ResourceType.SOLAR)));
                game.getInventory(player).addResources(choosenResource, 1);
            },
            "Les Ailes de la Gardienne",
            true,
            "%s reçevra 1 Or ou 1 Solaire ou 1 Lunaire au début de ses prochain tours"), numOfCards);

        cardSanctuary.put(new Card(2, ResourceType.SOLAR, CardLocationType.SOLAR2,
            (Game game, Player player) -> {
                //L'adversaire perd des ressources
                game.getPlayers()
                    .stream().filter(player1 -> !player1.equals(player))
                    .forEach(player1 ->
                        {
                            Inventory inventory = game.getInventory(player1);
                            int oldGold = inventory.getResource(ResourceType.GOLD);
                            int oldGlory = inventory.getResource(ResourceType.GLORY);
                            int oldSolar = inventory.getResource(ResourceType.SOLAR);
                            int oldLunar = inventory.getResource(ResourceType.LUNAR);
                            inventory.throwDices()
                                .forEach(facet -> facet.getOperation().apply(game, player1));
                            int newGold = inventory.getResource(ResourceType.GOLD);
                            int newGlory = inventory.getResource(ResourceType.GLORY);
                            int newSolar = inventory.getResource(ResourceType.SOLAR);
                            int newLunar = inventory.getResource(ResourceType.LUNAR);

                            inventory.addResources(ResourceType.GOLD, -2 * (newGold - oldGold));
                            inventory.addResources(ResourceType.GLORY, -2 * (newGlory - oldGlory));
                            inventory.addResources(ResourceType.SOLAR, -2 * (newSolar - oldSolar));
                            inventory.addResources(ResourceType.LUNAR, -2 * (newLunar - oldLunar));
                        }
                    );
            }, "Le Minotaure",
            false,
            "Les adversaire de %s perdent lance les dés et perdent les ressource au lieu de les gagner"), numOfCards);

        cardSanctuary.put(new Card(4, ResourceType.SOLAR, CardLocationType.SOLAR3,
            //Pas d'effet cette garde ne donne que 14 Gloire
            (Game game, Player player) -> game.getInventory(player).addResources(ResourceType.GLORY, 14),
            "La Meduse",
            "%s a gagné 14 Gloire"), numOfCards);

        cardSanctuary.put(new Card(5, ResourceType.SOLAR, CardLocationType.SOLAR3,
            (Game game, Player player) -> {
                Inventory inventory = game.getInventory(player);
                Facet facetToForge = new Facet("MIRROR",
                    (Game facetGame, Player facetPlayer) -> game.getPlayers()
                        .stream().filter(player1 -> !player1.equals(player))
                        .forEach(player1 -> {
                                List<Facet> oppFaceUp = game.getInventory(player1).getFaceUp();
                                Facet facet = player.chooseFacetToApply(oppFaceUp, game);
                                facet.getOperation().apply(game, player);
                            }
                        ));
                int[] diceChangeFace = player.forgeDice(game, facetToForge);
                inventory.forge(facetToForge, diceChangeFace[0], diceChangeFace[1]);
            },
            "Le Miroir Abyssal",
            "%s a reçu la face de dés du Miroir Abyssal et la forge"), numOfCards);

        HashMap<Player, CardLocationType> locations = new HashMap<>();
        players.forEach(player -> locations.put(player, CardLocationType.CENTER));

        return new CardSanctuary(cardSanctuary, locations);
    }

    public static List<Dice> generateDices() {
        Facet gold = new Facet("1 GOLD",
            ((Game game, Player player) -> game.getInventory(player).addGold(player, 1)));

        Facet solar = new Facet("1 SOLAR",
            ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.SOLAR, 1)));

        Facet lunar = new Facet("1 LUNAR",
            ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.LUNAR, 1)));

        Facet glory = new Facet("2 GLORY",
            ((Game game, Player player) -> game.getInventory(player).addResources(ResourceType.GLORY, 2)));

        Dice d1 = new Dice(new ArrayList<Facet>() {{
            add(gold);
            add(gold);
            add(gold);
            add(gold);
            add(gold);
            add(solar);
        }}
        );
        Dice d2 = new Dice(new ArrayList<Facet>() {{
            add(gold);
            add(gold);
            add(gold);
            add(gold);
            add(lunar);
            add(glory);
        }}
        );

        return new ArrayList<Dice>() {{
            add(d1);
            add(d2);
        }};
    }

    public static Inventory generateInventory(int gold) {

        Inventory inventory = new Inventory(generateDices());
        inventory.addResources(ResourceType.GOLD, gold);

        return inventory;
    }

    public static TreeMap<Player, Inventory> generateInventories(List<Player> players) {
        TreeMap<Player, Inventory> inventories = new TreeMap<>();

        for (int i = 0; i < players.size(); i++) {
            inventories.put(players.get(i), generateInventory(3 - i));
        }

        return inventories;
    }
}
