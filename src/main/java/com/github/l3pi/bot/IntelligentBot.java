package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class IntelligentBot extends Player{
    private Random gen;
    private CardLocationType location = null;

    private HashMap<String,Double> facetCoefficient;

    /** Cette classe représente un bot qui effectue les actions avec décision
     * @param name le nom du bot
     *
     * */
    public IntelligentBot(String name,int maxRound) {
        super(name);
        gen = new Random();

        this.facetCoefficient.put("1 GOLD",(double)maxRound*1);
        this.facetCoefficient.put("3 GOLD",(double)maxRound*3);
        this.facetCoefficient.put("4 GOLD",(double)maxRound*4);
        this.facetCoefficient.put("6 GOLD",(double)maxRound*6);
        this.facetCoefficient.put("1 SOLAR",(double)maxRound*2.5);
        this.facetCoefficient.put("2 SOLAR",(double)maxRound*7);
        this.facetCoefficient.put("1 LUNAR",(double)maxRound*2);
        this.facetCoefficient.put("2 LUNAR",(double)maxRound*5);
        this.facetCoefficient.put("3 GLORY",(double)maxRound*7);
        this.facetCoefficient.put("1 GLORY + SOLAR",(double)maxRound*4);
        this.facetCoefficient.put("2 GOLD + 1 LUNAR",(double)maxRound*4);
        this.facetCoefficient.put("2 GLORY + LUNAR",(double)maxRound*12);
        this.facetCoefficient.put("1 GOLD + LUNAR + GLORY + SOLAR",(double)maxRound*12);

    }

    /** Cette fonction prend le game en parametre pour recuperer toute ses informations concernant et game
     * gere les actions que veut faire le bot
     * cette fonction permet au bot de  une face de dé meilleur parmi les faces que le joueur posséde
     *
     * @param game prend game , qui est le tour en cours
     * @return la facet choisis
     * */

    @Override
    public Facet chooseDiceFacet(Game game) {
        List<Facet> purchasableFacets = game.getDiceSanctuary().getPurchasableInventory(game.getInventory(this).getResource(ResourceType.GOLD));

        List<Dice> mydices = game.getInventory(this).getDices();
        for(Dice dice: mydices){
            for(int i= 0;i<6;i++){
                for(Facet facet:purchasableFacets){
                    if(this.facetCoefficient.get(dice.getFacet(i).getName()) < this.facetCoefficient.get(facet)){
                        return facet;
                    }
                }
            }
        }
        return null;
    }

    /** Cette fonction prend le game en parametre pour recuperer toute ses informations concernant et game

     * @param game prend game , qui est le tour en cours
     * @return la carte choisis
     * */

    @Override
    public Card chooseCard(Game game){
        HashMap<String,Double> cardCoefficient = new HashMap<String, Double>(){};

        cardCoefficient.put("Le Coffre du Forgeron",(double)12);
        cardCoefficient.put("Les Sabots d'Argent",(double)12);
        cardCoefficient.put("Les Satyres",(double)(6));
        cardCoefficient.put("Le Casque d'invisibilité",(double)4);
        cardCoefficient.put("La Pince",(double)8);
        cardCoefficient.put("L'hydre",(double)(4+4*3));
        cardCoefficient.put("L'Enigme",(double)(4+4*3));
        cardCoefficient.put("L'Ancien",(double)(4+4*3));
        cardCoefficient.put("Les Herbes Folles",(double)(4+4*3));
        cardCoefficient.put("Les Ailes de la Gardienne",(double)(4+4*3));
        cardCoefficient.put("Le Minotaure",(double)(4+4*3));
        cardCoefficient.put("La Meduse",(double)(4+4*3));
        cardCoefficient.put("Le Miroir Abyssal",(double)(4+4*3));

        List<Card> purchasableCards = game.get().getPurchasableCard(game.getInventory(this));
        System.out.println("choix des cartes achetables :" + purchasableCards);

        Card BestCard;

        for(Card card:purchasableCards){
            if(card.getName() == "Le Coffre du Forgeron" ){

            }
            else if(card.getName() == "Les Sabots d'Argent"){

            }
            else if(card.getName() == "Les Satyres"){

            }
            else if(card.getName() == "Le Casque d'invisibilité"){

            }
            else if(card.getName() == "La Pince"){

            }
            else if(card.getName() == "L'hydre"){

            }
            else if(card.getName() == "L'Enigme"){

            }
            else if(card.getName() == "L'Ancien"){

            }
            else if(card.getName() == "Les Herbes Folles"){

            }
            else if(card.getName() == "Les Ailes de la Gardienne"){

            }
            else if(card.getName() == "Le Minotaure"){

            }
            else if(card.getName() == "La Meduse" ){

            }
            else if(card.getName() == "Le Miroir Abyssal"){
                
            }
        }

        return null;
    }
    /** Choisis aléatoirement une action
     * @param game le tour en cours
     * @return
     * 0 signifie acheter une face de dé
     * 1 signifie acheter une carte
     * */

    @Override
    public int chooseAction(Game game){
        return this.gen.nextInt(2);
    }

    /** Cette fonction est utilisé par les effects de cartes et non directement par le bot lui meme
     * c'est la carte qui demande au joueur de faire une action
     * @param resource liste de ressources , voir carte correspondante "Les Ailes de la Gardienne"
     * */

    @Override
    public ResourceType chooseResource(List<ResourceType> resource){
        Collections.shuffle(resource);
        return  resource.get(0);
    }

    /** Cette fonction est utilisé par les effects de cartes et non directement par le bot lui meme
     * c'est la carte qui demande au joueur de faire une action
     * @param game le tour en cours , voir carte correspondante L'Ancien
     * */


    @Override
    public boolean tradeGoldForGlory(Game game){
        return gen.nextBoolean();
    }

    /** Cette fonction est utilisé pour le tour en cours , le joueur choisis un dé de ses 2 dé pour forger une face
     * ou autre actons
     * @param dices la listes des dés
     * @return un dés choisi aléatoirement
     *
     * */


    @Override
    public int chooseDice(List<Dice> dices){
        return this.gen.nextInt(dices.size());
    };

    /** Cette fonction prend le tour en cours et une face de dé et choisis quel dé et quel face
     * forger la nouvelle face de dé acheté depuis le sanctuaire des dés
     *
     * @param game le tour en cours
     * @param facet la face de dé a forger
     *
     * */


    @Override
    public int[] forgeMyDice(Game game,Facet facet) {

        return new int[]{this.gen.nextInt(1),this.gen.nextInt(5)};
    }

    /** Function spéciale pour goldHammer
     * @param gold le nombre de gold
     * @param inventory  l'inventaire du joueur
     *
     * */

    @Override
    public Tuple<Integer, Integer> chooseGoldRepartion(Inventory inventory, int gold) {
        //TODO : Implement Balance
        return new Tuple<>(0,0);
    }

}
