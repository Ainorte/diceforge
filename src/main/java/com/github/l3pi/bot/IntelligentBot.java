package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.*;

public class IntelligentBot extends RandomBot{
    private Random gen;
    private CardLocationType location = null;
    private int maxRound;
    private HashMap<String,Double> facetCoefficient = new HashMap<>();

    /** Cette classe représente un bot qui effectue les actions avec décision
     * @param name le nom du bot
     *
     * */
    public IntelligentBot(String name) {
        super(name);
        gen = new Random();
        this.maxRound = 9;

        this.facetCoefficient.put("1 GOLD",(double)maxRound*1);
        this.facetCoefficient.put("3 GOLD",(double)maxRound*3);
        this.facetCoefficient.put("4 GOLD",(double)maxRound*4);
        this.facetCoefficient.put("6 GOLD",(double)maxRound*6);
        this.facetCoefficient.put("1 SOLAR",(double)maxRound*2.5);
        this.facetCoefficient.put("2 SOLAR",(double)maxRound*7);
        this.facetCoefficient.put("1 LUNAR",(double)maxRound*2);
        this.facetCoefficient.put("2 LUNAR",(double)maxRound*5);
        this.facetCoefficient.put("2 GLORY",(double)maxRound*5);
        this.facetCoefficient.put("3 GLORY",(double)maxRound*7);
        this.facetCoefficient.put("1 GLORY + SOLAR",(double)maxRound*4);
        this.facetCoefficient.put("2 GOLD + 1 LUNAR",(double)maxRound*4);
        this.facetCoefficient.put("2 GLORY + LUNAR",(double)maxRound*12);
        this.facetCoefficient.put("1 GOLD + LUNAR + GLORY + SOLAR",(double)maxRound*12);
        this.facetCoefficient.put("1 GOLD / LUNAR / SOLAR",(double)maxRound*12);
        this.facetCoefficient.put("2 GOLD / LUNAR / SOLAR",(double)maxRound*10);

        this.facetCoefficient.put("x3",(double)maxRound*4);
        this.facetCoefficient.put("MIRROR",(double)maxRound*0);




    }

    /** Cette fonction prend le game en parametre pour recuperer toute ses informations concernant et game
     * gere les actions que veut faire le bot
     * cette fonction permet au bot de  une face de dé meilleur parmi les faces que le joueur posséde
     *
     * @param game prend game , qui est le tour en cours
     * @return la facet choisis
     * */

    @Override
    public Facet chooseFacetToForge(List<Facet> facetList, Game game) {


        List<Dice> mydices = game.getInventory(this).getDices();
        for(Dice dice: mydices){
            for(int i= 0;i<6;i++){
                for(Facet facet:facetList){
                    if(this.facetCoefficient.get(dice.getFacet(i).getName()) < this.facetCoefficient.get(facet.getName())){
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

        cardCoefficient.put("Le Marteau du Forgeron",(double)12);
        cardCoefficient.put("Le Coffre du Forgeron",(double)12);

        cardCoefficient.put("Les Sabots d'Argent",(double)12);
        cardCoefficient.put("Les Satyres",(double)(6));

        cardCoefficient.put("Le Passeur",(double)5);
        cardCoefficient.put("Le Casque d'invisibilité",(double)6);

        cardCoefficient.put("La Pince",(double)8);
        cardCoefficient.put("L'Hydre",(double)(4+4*3));
        cardCoefficient.put("L'Enigme",(double)(4+4*3));

        cardCoefficient.put("L'Ancien",(double)(4+4*3));
        cardCoefficient.put("Les Herbes Folles",(double)(4+4*3));

        cardCoefficient.put("Les Ailes de la Gardienne",(double)(4+4*3));
        cardCoefficient.put("Le Minotaure",(double)(4+4*3));

        cardCoefficient.put("La Meduse",(double)(4+4*3));
        cardCoefficient.put("Le Miroir Abyssal",(double)(4+4*3));

        List<Card> purchasableCards = game.getCardSanctuary().getPurchasableCard(game.getInventory(this));
        if(purchasableCards.size() == 0){
            return null;
        }

        Card BestCard = purchasableCards.get(0);
        int coef = 0 ;
        for(Card card:purchasableCards){
            int tmp = 0;
            if(card.getName() == "Le Coffre du Forgeron" ){
                tmp = game.getInventory(this).getResource(ResourceType.GOLD)*20;
            }
            if(card.getName() == "Les Sabots d'Argent"){
                List<Dice> dices = game.getInventory(this).getDices();
                int diceCoef=0;
                for(Dice dice:dices){
                    int c =0;
                    for(int i=0;i<6;i++){
                        c += this.facetCoefficient.get(dice.getFacet(i).getName());
                    }
                    if(diceCoef < c){
                        diceCoef = c;
                    }

                }
                tmp = diceCoef*(this.maxRound-game.getActualRound());
            }
            if(card.getName() == "Les Satyres"){
                Set<Player> players = game.getPlayers();
                int co = 0;
                for(Player player:players){

                    if(!player.getName().equals(this.getName())){
                        List<Dice> dices = game.getInventory(player).getDices();
                        int c = 0;
                        for(Dice dice:dices){
                            for(int i =0;i<6;i++){
                                c+= this.facetCoefficient
                                    .get(
                                    dice.getFacet(i)
                                        .getName());
                            }
                            if(co < c){
                                co = c;
                            }
                        }

                    }
                }
                tmp = co;


            }
            if(card.getName() == "Le Casque d'invisibilité"){
                List<Dice> dices = game.getInventory(this).getDices();
                int c = 0;
                for(Dice dice:dices){
                    int co = 0;

                    for(int j=0;j<6;j++){
                        co += this.facetCoefficient.get(dice.getFacet(j).getName())*3;
                    }


                    if(c < co){
                        c = co;
                }
                }
                tmp = c;

            }
            if(card.getName() == "La Pince"){
                List<Dice> dices = game.getInventory(this).getDices();
                int co = 0;
                for(Dice dice:dices){
                    for(int j=0;j<6;j++){
                        co += this.facetCoefficient.get(dice.getFacet(j).getName())*2;
                    }
                }
                tmp = co;

            }
            if(card.getName() == "L'hydre"){
                tmp = 26*3;
            }
            if(card.getName() == "L'Enigme"){
                List<Dice> dices = game.getInventory(this).getDices();
                int co = 0;
                for(Dice dice:dices){
                    int d = 0 ;
                    for(int j=0;j<6;j++){
                        d += this.facetCoefficient.get(dice.getFacet(j).getName())*4;
                    }
                    if(co<d){
                        co = d;
                    }
                }
                tmp = co;
            }
            if(card.getName() == "L'Ancien"){
                tmp = 40;
            }
            if(card.getName() == "Les Herbes Folles"){
                tmp = 21;
            }
            if(card.getName() == "Les Ailes de la Gardienne"){
                tmp = maxRound*5;
            }
            if(card.getName() == "Le Minotaure"){
                tmp =  10;
            }
            if(card.getName() == "La Meduse" ){
                tmp = 14*3;
            }
            if(card.getName() == "Le Miroir Abyssal"){
                tmp = 20;
            }

            if(coef<tmp){
                coef = tmp;
                BestCard = card;
            }
        }
        return BestCard;

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
        List<Dice> dices = game.getInventory(this).getDices();
        int d = 0;
        int dcoef = 999999999;
        int f = 0;
        double fcoef = 99999.0;

        for(int indice = 0;indice<2;indice++){

            int tmp = 0;
            for(int i =0;i<6;i++){
                tmp += this.facetCoefficient
                    .get(dices.get(indice)
                        .getFacet(i)
                            .getName());
            }
            if(dcoef>tmp){
                dcoef=tmp;
                d = indice;
            }

        }

        Dice diceChoosen = dices.get(d);
        for(int i =0;i<6;i++) {
            if(fcoef>this.facetCoefficient.get(diceChoosen.getFacet(i).getName())){
                fcoef = this.facetCoefficient.get(diceChoosen.getFacet(i).getName());
                f = i;
            }


        }

        return new int[]{d,f};
    }

    /** Function spéciale pour goldHammer
     * @param gold le nombre de gold
     * @param inventory  l'inventaire du joueur
     *
     * */

    @Override
    public Tuple<Integer, Integer> chooseGoldRepartion(Inventory inventory, int gold) {
        int inv = inventory.getMaxRessources(ResourceType.GOLD) - inventory.getResource(ResourceType.GOLD);
        int hammer = (30 - inventory.getHammerGold()) + (inventory.getActiveHammerCardCount()-1)*30;

        if(gen.nextInt(2) == 0){
            int i = gold <= inv ? gold : inv;
            int h = gold - i <= hammer ? gold - i : hammer;
            return new Tuple<>(i,h);
        }
        else {
            int h = gold <= hammer ? gold : hammer;
            int i = gold - h <= inv ? gold - h : inv;
            return new Tuple<>(i,h);
        }
    }

}
