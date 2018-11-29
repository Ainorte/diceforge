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
    private TreeMap<Player, Inventory> players;
    private DiceSanctuary diceSanctuary;
    private CardSanctuary cardSanctuary;

    public Game(List<Player> players) {
        this.players = new TreeMap<>();
        this.diceSanctuary = new DiceSanctuary();
        this.cardSanctuary = new CardSanctuary();

        for (int i =0; i < players.size(); i++) {
            this.players.put(players.get(i), new Inventory(3 - i));
        }
    }

    void round() {
        for (Player player : this.getPlayers()) {
            round(player);
        }

    }

    private void round(Player player) {

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

    private void divineBlessing() {

        TreeMap<Player, List<Tuple<Integer, Operation>>> operations = new TreeMap<>();
        for (Player player : getPlayers()) {
            List<Facet> facetUp = getInventory(player).throwDices();

            log(player.getName() + " a lancé " + facetUp);

            List<Tuple<Integer,Operation>> ops = new ArrayList<>();
            for(Facet facet : facetUp){
                ops.add(facet.getOperations());
            }
            operations.put(player,ops);
        }
        for (Map.Entry<Player, List<Tuple<Integer, Operation>>> playerOps: operations.entrySet()) {
            for(Tuple<Integer,Operation> ops : playerOps.getValue()){
                ops.getY().apply(this,playerOps.getKey());
            }
        }
    }

    private void action(Player player){

        if(player.chooseAction(this) == 0) {
            Facet facet = player.chooseDiceFacet(this);
            if (facet != null) {
                facet = this.diceSanctuary.buyFacet(facet);
                log(player.getName() + " a acheté la face de dés " + facet + " pour " + this.diceSanctuary.getPriceForFacet(facet)+ "Or");
                int[] diceChangeFace = player.forgeMyDice(this, facet);
                this.getInventory(player).forge(facet, diceChangeFace[0], diceChangeFace[1]);
            }
        }
        else{
            Card card = player.chooseCard(this);
            if (card != null) {
                card = this.cardSanctuary.buyCard(card);
                Inventory inventory = this.players.get(player);
                inventory.addCard(card);
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

    public Inventory getInventory(Player player) {
        return players.get(player);
    }

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





    //TODO P2 : GAME STATE HISTORY

    private String toString(Player player) {
        return player.toString() + "\n" + players.get(player).toString();
    }

    @Override
    public String toString() {
        return getPlayers().stream().map(this::toString).collect(Collectors.joining("\n\n"));
    }
}
