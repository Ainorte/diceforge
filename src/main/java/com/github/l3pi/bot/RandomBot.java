package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.github.l3pi.sys.Log.log;

public class RandomBot extends Player {
    private Random gen;
    private CardLocationType location = null;


    /** Cette classe représente un bot qui effectue les actions aléatoirement
     * @param name le nom du bot
     *
     * */
    public RandomBot(String name) {
        super(name);
        gen = new Random();
    }

    /** Cette fonction prend le game en parametre pour recuperer toute ses informations concernant et game
     * gere les actions que veut faire le bot
     * cette fonction permet au bot de choisir aléatoirement une face de dé dans le sanctuaire des dé
     *
     * @param game prend game , qui est le tour en cours
     * @return la facet choisis
     * */

    @Override
    public Facet chooseDiceFacet(List<Facet> facetList) {
        if (facetList.size() > 0) {
            return facetList.get(this.gen.nextInt(facetList.size()));
        }
        return null;
    }

    /** Cette fonction prend le game en parametre pour recuperer toute ses informations concernant et game
     * gere les actions que veut faire le bot
     * cette fonction permet au bot de choisir aléatoirement une carte dans le sanctuaire des cartes
     *
     * @param game prend game , qui est le tour en cours
     * @return la carte choisis
     * */

    @Override
    public Card chooseCard(Game game){
        List<Card> purchasableCards = game.getCardSanctuary().getPurchasableCard(game.getInventory(this));
        if (purchasableCards.size() > 0) {
            return purchasableCards.get(this.gen.nextInt(purchasableCards.size()));
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
