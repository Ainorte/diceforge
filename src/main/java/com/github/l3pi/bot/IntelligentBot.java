package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class IntelligentBot extends Player {
    private Random gen;
    private CardLocationType location = null;
    private int maxRound;
    private HashMap<String, Integer> facetCoefficient = new HashMap<>();

    /**
     * Cette classe représente un bot qui effectue les actions avec décision
     *
     * @param number le numero du bot
     *               <p>
     *               Gloire == victoire
     *               je commence dés un 5 a 7 or dés avec 1 lune
     *               ensuite , or et victoire , victoire olei , victoire lune , grosse fasse victoire ,
     *               l'autre dé x3 soleil , triple selection a double ressource
     */
    public IntelligentBot(int number) {
        super(number);
        gen = new Random();
        this.maxRound = 9;

        this.facetCoefficient.put("1 GOLD", 1);
        this.facetCoefficient.put("3 GOLD", 3);
        this.facetCoefficient.put("4 GOLD", 4);
        this.facetCoefficient.put("6 GOLD", 6);
        this.facetCoefficient.put("1 SOLAR", 2);
        this.facetCoefficient.put("2 SOLAR", 4);
        this.facetCoefficient.put("1 LUNAR", 3);
        this.facetCoefficient.put("2 LUNAR", 6);
        this.facetCoefficient.put("2 GLORY", 8);
        this.facetCoefficient.put("3 GLORY", 12);
        this.facetCoefficient.put("4 GLORY", 16);
        this.facetCoefficient.put("1 GLORY + SOLAR", 6);
        this.facetCoefficient.put("2 GOLD + 1 LUNAR", 5);
        this.facetCoefficient.put("2 GLORY + LUNAR", 11);
        this.facetCoefficient.put("1 GOLD + LUNAR + GLORY + SOLAR", 11);
        this.facetCoefficient.put("1 GOLD / LUNAR / SOLAR", 6);
        this.facetCoefficient.put("2 GOLD / LUNAR / SOLAR", 7);
        this.facetCoefficient.put("3 GOLD / 2 GLORY", 11);

        this.facetCoefficient.put("x3", 20);
        this.facetCoefficient.put("MIRROR", 20);

    }

    /**
     * Cette fonction prend le game en parametre pour recuperer toute ses informations concernant et game
     * gere les actions que veut faire le bot
     * cette fonction permet au bot de  une face de dé meilleur parmi les faces que le joueur posséde
     *
     * @param game prend game , qui est le tour en cours
     * @return la facet choisis
     */

    @Override
    public Facet chooseFacetToForge(List<Facet> facetList, Game game) {


        List<Dice> mydices = game.getInventory(this).getDices();
        Dice dice1 = mydices.get(0);
        Dice dice2 = mydices.get(1);

        List<Facet> facetGold = facetList.stream().filter(x -> x.getName().contains("GOLD")).collect(Collectors.toList());

        if (game.getRound() <= 3 &&
            (dice1.getFacets().stream().mapToInt(x -> this.facetCoefficient.get(x.getName())).sum() <= 9) &&
            (dice1.getFacets().stream().filter(x -> x.getName().contains("GOLD")).count()) >= 5 &&
            facetGold.size()> 0) {


            int bestFacetcoef = this.facetCoefficient.get("1 GOLD");
            Facet f = facetGold.get(0);
            for (Facet facet : facetGold) {
                if (bestFacetcoef < this.facetCoefficient.get(facet.getName())) {
                    bestFacetcoef = this.facetCoefficient.get(facet.getName());
                    f = facet;
                }
            }
            return f;

        } else if (game.getRound() >= 2 && (dice1.getFacets().stream().mapToInt(x -> this.facetCoefficient.get(x.getName())).sum() > 9)) {

            for (Facet facet : facetList) {
                if (facet.getName().equals("1 GOLD + LUNAR + GLORY + SOLAR")) {
                    return facet;
                }
            }

            for (Facet facet : facetList) {
                if (facet.getName().equals("3 GOLD / 2 GLORY")) {
                    return facet;
                }
            }

            for (Facet facet : facetList) {
                if (facet.getName().equals("1 GLORY + SOLAR")) {
                    return facet;
                }
            }
            for (Facet facet : facetList) {
                if (facet.getName().equals("2 GLORY + LUNAR")) {
                    return facet;
                }
            }

            for (Facet facet : facetList) {
                if (facet.getName().equals("2 GOLD / LUNAR / SOLAR")) {
                    return facet;
                }
            }

            List<Facet> facetsGlory = facetList.stream()
                .filter(x -> x.getName().contains("4 GLORY") || x.getName().contains("3 GLORY") || x.getName().contains("2 GLORY"))
                .collect(Collectors.toList());

            if (facetsGlory.size() != 0) {
                int fcoef = 0;
                Facet coucou = facetsGlory.get(0);
                for (Facet facet : facetsGlory) {
                    if (fcoef < this.facetCoefficient.get(facet.getName())) {
                        fcoef = this.facetCoefficient.get(facet.getName());
                        coucou = facet;
                    }
                }
                return coucou;
            }

        }
        return null;
    }

    @Override
    public Facet chooseFacetToApply(List<Facet> facetList, Game game) {
        return null;
    }

    /**
     * Cette fonction prend le game en parametre pour recuperer toute ses informations concernant et game
     *
     * @param game prend game , qui est le tour en cours
     * @return la carte choisis
     */

    @Override
    public Card chooseCard(Game game) {
        HashMap<String, Double> cardCoefficient = new HashMap<String, Double>() {
        };

        cardCoefficient.put("Le Marteau du Forgeron", (double) 12);
        cardCoefficient.put("Le Coffre du Forgeron", (double) 12);

        cardCoefficient.put("Les Sabots d'Argent", (double) 12);
        cardCoefficient.put("Les Satyres", (double) (6));

        cardCoefficient.put("Le Passeur", (double) 5);
        cardCoefficient.put("Le Casque d'invisibilité", (double) 6);

        cardCoefficient.put("La Pince", (double) 8);
        cardCoefficient.put("L'Hydre", (double) (4 + 4 * 3));
        cardCoefficient.put("L'Enigme", (double) (4 + 4 * 3));

        cardCoefficient.put("L'Ancien", (double) (4 + 4 * 3));
        cardCoefficient.put("Les Herbes Folles", (double) (4 + 4 * 3));

        cardCoefficient.put("Les Ailes de la Gardienne", (double) (4 + 4 * 3));
        cardCoefficient.put("Le Minotaure", (double) (4 + 4 * 3));

        cardCoefficient.put("La Meduse", (double) (4 + 4 * 3));
        cardCoefficient.put("Le Miroir Abyssal", (double) (4 + 4 * 3));

        List<Card> purchasableCards = game.getCardSanctuary().getPurchasableCard(game.getInventory(this).getResources());
        if (purchasableCards.size() == 0) {
            return null;
        }

        Card BestCard = purchasableCards.get(0);
        int coef = 0;
        for (Card card : purchasableCards) {
            int tmp = 0;
            if (card.getName().equals("Le Coffre du Forgeron")) {
                tmp = game.getInventory(this).getResource(ResourceType.GOLD) * 20;
            }
            if (card.getName().equals("Les Sabots d'Argent")) {
                List<Dice> dices = game.getInventory(this).getDices();
                int diceCoef = 0;
                for (Dice dice : dices) {
                    int c = 0;
                    for (int i = 0; i < 6; i++) {
                        c += this.facetCoefficient.get(dice.getFacet(i).getName());
                    }
                    if (diceCoef < c) {
                        diceCoef = c;
                    }

                }
                tmp = diceCoef * (this.maxRound - game.getRound());
            }
            if (card.getName().equals("Les Satyres")) {
                HashMap<Integer, Inventory> inventories = game.getInventories();
                int co = 0;
                for (Map.Entry<Integer, Inventory> inventory : inventories.entrySet()) {

                    if (!inventory.getKey().equals(this.getId())) {
                        List<Dice> dices = inventory.getValue().getDices();
                        int c = 0;
                        for (Dice dice : dices) {
                            for (int i = 0; i < 6; i++) {
                                c += this.facetCoefficient
                                    .get(
                                        dice.getFacet(i)
                                            .getName());
                            }
                            if (co < c) {
                                co = c;
                            }
                        }

                    }
                }
                tmp = co;


            }
            if (card.getName().equals("Le Casque d'invisibilité")) {
                List<Dice> dices = game.getInventory(this).getDices();
                int c = 0;
                for (Dice dice : dices) {
                    int co = 0;

                    for (int j = 0; j < 6; j++) {
                        co += this.facetCoefficient.get(dice.getFacet(j).getName()) * 3;
                    }


                    if (c < co) {
                        c = co;
                    }
                }
                tmp = c;

            }
            if (card.getName().equals("La Pince")) {
                List<Dice> dices = game.getInventory(this).getDices();
                int co = 0;
                for (Dice dice : dices) {
                    for (int j = 0; j < 6; j++) {
                        co += this.facetCoefficient.get(dice.getFacet(j).getName()) * 2;
                    }
                }
                tmp = co;

            }
            if (card.getName().equals("L'hydre")) {
                tmp = 26 * 3;
            }
            if (card.getName().equals("L'Enigme")) {
                List<Dice> dices = game.getInventory(this).getDices();
                int co = 0;
                for (Dice dice : dices) {
                    int d = 0;
                    for (int j = 0; j < 6; j++) {
                        d += this.facetCoefficient.get(dice.getFacet(j).getName()) * 4;
                    }
                    if (co < d) {
                        co = d;
                    }
                }
                tmp = co;
            }
            if (card.getName().equals("L'Ancien")) {
                tmp = this.maxRound * 3;
            }
            if (card.getName().equals("Les Herbes Folles")) {
                tmp = 21;
            }
            if (card.getName().equals("Les Ailes de la Gardienne")) {
                tmp = maxRound * 5;
            }
            if (card.getName().equals("Le Minotaure")) {
                tmp = 10 * 3;
            }
            if (card.getName().equals("La Meduse")) {
                tmp = 14 * 3;
            }
            if (card.getName().equals("Le Miroir Abyssal")) {
                tmp = 35;
            }

            if (coef < tmp) {
                coef = tmp;
                BestCard = card;
            }
        }
        return BestCard;

    }

    /**
     * Choisis aléatoirement une action
     *
     * @param game le tour en cours
     * @return 0 signifie acheter une face de dé
     * 1 signifie acheter une carte
     */

    @Override
    public int chooseAction(Game game) {
        List<Card> cards = game.getCardSanctuary().getPurchasableCard(game.getInventory(this).getResources());
        if (cards.size() >= 8) {
            return 1;
        }
        return 0;
    }

    /**
     * Cette fonction est utilisé par les effects de cartes et non directement par le bot lui meme
     * c'est la carte qui demande au joueur de faire une action
     *
     * @param resource liste de ressources , voir carte correspondante "Les Ailes de la Gardienne"
     */

    @Override
    public ResourceType chooseResource(List<ResourceType> resource) {
        Collections.shuffle(resource);
        return resource.get(0);
    }

    /**
     * Cette fonction est utilisé par les effects de cartes et non directement par le bot lui meme
     * c'est la carte qui demande au joueur de faire une action
     *
     * @param game le tour en cours , voir carte correspondante L'Ancien
     */


    @Override
    public boolean tradeGoldForGlory(Game game) {
        return gen.nextBoolean();
    }

    /**
     * Cette fonction est utilisé pour le tour en cours , le joueur choisis un dé de ses 2 dé pour forger une face
     * ou autre actons
     *
     * @param dices la listes des dés
     * @return un dés choisi aléatoirement
     */


    @Override
    public int chooseDice(List<Dice> dices) {
        return this.gen.nextInt(dices.size());
    }

    /**
     * Cette fonction prend le tour en cours et une face de dé et choisis quel dé et quel face
     * forger la nouvelle face de dé acheté depuis le sanctuaire des dés
     *
     * @param game  le tour en cours
     * @param facet la face de dé a forger
     */


    @Override
    public int[] forgeDice(Game game, Facet facet) {
        List<Dice> dices = game.getInventory(this).getDices();
        int d = 0;
        int dcoef = 999999999;
        int f = 0;
        double fcoef = 99999.0;

        for (int indice = 0; indice < 2; indice++) {

            int tmp = 0;
            for (int i = 0; i < 6; i++) {
                tmp += this.facetCoefficient
                    .get(dices.get(indice)
                        .getFacet(i)
                        .getName());
            }
            if (dcoef > tmp) {
                dcoef = tmp;
                d = indice;
            }

        }

        Dice diceChoosen = dices.get(d);
        for (int i = 0; i < 6; i++) {
            if (fcoef > this.facetCoefficient.get(diceChoosen.getFacet(i).getName())) {
                fcoef = this.facetCoefficient.get(diceChoosen.getFacet(i).getName());
                f = i;
            }


        }

        return new int[]{d, f};
    }

    /**
     * Function spéciale pour goldHammer
     *
     * @param gold      le nombre de gold
     * @param inventory l'inventaire du joueur
     */

    @Override
    public Tuple<Integer, Integer> chooseGoldRepartion(Inventory inventory, int gold) {
        return new Tuple<>(0, 0);
    }

    @Override
    public boolean moreAction(Game game) {
        return true;
    }

}
