package com.github.l3pi.game;


import com.github.l3pi.type.ResourceType;
import com.github.l3pi.utilities.Tuple;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.Log.log;

/**
 * Game instance
 */
public class Game {
    /**
     * la classe Game gere tout ce qui est interaction du jeu pour chaque tour, pour faire un jeu complet voir GameManager
     * qui executer le nombre de tours correspondant aux nombres de joueurs
     *
     * la variable players est une map ordonnée de la classe player qui associe son inventaire
     * la variable diceSanctuary est la variable qui représente le sanctuaire des dés
     * la variable cardSanctuary est la variable qui représente le sanctuaire des cartes
     *
     * */

    private TreeMap<Player, Inventory> players;
    private DiceSanctuary diceSanctuary;
    private CardSanctuary cardSanctuary;
    private int actualRound; // count the round for intelligentBot

    /** On instancie Game avec une liste de joueurs
     * @param players  a liste de joueurs pour cette partie
     *
     * */
    public Game(List<Player> players) {
        this.players = new TreeMap<>();
        this.diceSanctuary = new DiceSanctuary();
        this.cardSanctuary = new CardSanctuary();
        this.actualRound = 0;
        for (int i =0; i < players.size(); i++) {
            this.players.put(players.get(i), new Inventory(3 - i));
        }
    }

    public void increaseRound(){
        this.actualRound++;
    }

    public int getActualRound(){
        return this.actualRound;
    }


    /** applique un tour de jeu
     * */

    void round() {
        for (Player player : this.getPlayers()) {
            round(player);
        }

    }

    /**Applique un tour de jeu et gere l'ordre des actions pendant un tour
     * */

    private void round(Player player) {

        if(getPlayers().size() == 2){
            divineBlessing();
        }
        divineBlessing();
        System.out.println();
        recurrentAction(player);
        action(player);
        System.out.println();
        System.out.println();

    }

    private void recurrentAction(Player player){
    getInventory(player)
        .getCards().stream()
        .filter(Card::isRecurrent)
        .forEach(card -> {card.getOperation().apply(this,player);});
    }

    /** l'action qui permet de lancer le dé des joueurs l'un aprés l'autre
     *
     * */
    private void divineBlessing() {
        for (Player player : getPlayers()) {
            List<Facet> facetUp = getInventory(player).throwDices();
            log(player.getName() + " a lancé " + facetUp);
        }
        for (Player player : getPlayers()) {
            getInventory(player)
                .getFaceUp()
                .forEach(facet -> {facet.getOperation().apply(this,player);});

        }
    }

    /** cette fonction applique une action en fonction du choix du joueur
     * par exemple le joueur peut decider d'acheter une carte ou de forger une face de dé
     * @param player le player qui effectue l'action
     * */
    private void action(Player player){

        if(player.chooseAction(this) == 0) {
            Facet facet = player.chooseDiceFacet(this);
            if (facet != null) {
                facet = this.diceSanctuary.buyFacet(facet);
                log(player.getName() + " a acheté la face de dés " + facet + " pour " + this.diceSanctuary.getPriceForFacet(facet)+ " Or");
                int[] diceChangeFace = player.forgeMyDice(this, facet);
                this.getInventory(player).forge(facet, diceChangeFace[0], diceChangeFace[1]);
            }
        }
        else{
            final Card card = player.chooseCard(this);
            if (card != null) {
                this.cardSanctuary.buyCard(card);
                Inventory inventory = this.players.get(player);
                inventory.addCard(card);
                card.getResourceType().forEach(resource -> {
                    inventory.addResources(resource,-card.getPrice());
                });

                this.players.put(player,inventory);
                if(!card.isRecurrent()){
                    card.getOperation().apply(this,player);
                }
                //TODO player.move(card.getLocationType());
                log(player.getName() + " a acheté la carte " + card +" et se situe sur la case "+ card.getLocationType()+" du plateau");
            }
        }
    }



    public DiceSanctuary getDiceSanctuary() {
        return diceSanctuary;
    }

    public CardSanctuary getCardSanctuary() {
        return cardSanctuary;
    }


    public Set<Player> getPlayers() {
        return players.keySet();
    }

    /** prend un player en parametre et retourne l'inventaire associé au joueur
     *
     * */

    public Inventory getInventory(Player player) {
        return players.get(player);
    }

    /** cette fonction renvoie les gagnants
     *
     * @return une liste contenant les gagnants
     *
     * */

    List<Player> getBestPlayer() {
        if (players.size() == 0) {
            return new ArrayList<>();
        }
        int max = players.values()
            .stream()
            .max(Comparator.comparingInt(inventory -> inventory.getResource(ResourceType.GLORY)))
            .get()
            .getResource(ResourceType.GLORY);
        return players
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().getResource(ResourceType.GLORY) == max)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /** Function pour Hammergold , permet de rajouter du gold sur la carte de deplacement de cette carte
     * @param player le joueur qui effectue l'action
     * @param gold le nombre de gold
     * */

    public void addGold(Player player, int gold){
        Inventory inventory = getInventory(player);
        if (inventory.getActiveHammerCardCount() > 0 && inventory.getMaxRessources(ResourceType.GOLD) > inventory.getResource(ResourceType.GOLD)) {
            Tuple<Integer, Integer> repartition = player.chooseGoldRepartion(inventory, gold);
            if (repartition.getY() + repartition.getX() <= gold) {
                inventory.addResources(ResourceType.GOLD, repartition.getX());
                inventory.addGoldHammer(repartition.getY());
            }
        } else if (inventory.getMaxRessources(ResourceType.GOLD) <= inventory.getResource(ResourceType.GOLD)) {
            inventory.addGoldHammer(gold);
        } else {
            inventory.addResources(ResourceType.GOLD, gold);
        }
    }



    //TODO P2 : GAME STATE HISTORY

    private String toString(Player player) {
        return player.toString() + "\n" + players.get(player).toString();
    }

    @Override
    public String toString() {
        return getPlayers().stream().map(this::toString).collect(Collectors.joining("\n\n"));
    }
}
