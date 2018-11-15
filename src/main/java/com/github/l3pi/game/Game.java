package com.github.l3pi.game;

import com.github.l3pi.factory.InventoryFactory;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.LogDAO.log;

/**
 * Game instance
 */
public class Game {
    private TreeMap<Player, Inventory> players;
    private FacetRuleManager facetRuleManager;
    private DiceSanctuary diceSanctuary;
    private CardSanctuary cardSanctuary;

    public Game(List<Player> players, RuleSet ruleSet) {
        this.players = new TreeMap<>();
        this.diceSanctuary = new DiceSanctuary();
        this.cardSanctuary = new CardSanctuary();

        for (int i = 0; i < players.size(); i++) {
            this.players.put(players.get(i), InventoryFactory.getInstance().getInventory(this, players.get(i), i));
        }

        this.facetRuleManager = new FacetRuleManager(ruleSet);
    }

    void round() {
        for (Player player : this.getPlayers()) {
            round(player);
        }

    }

    private void round(Player player) {

        divineBlessing();
        action(player);

    }

    private void divineBlessing() {

        List<TempPlayer> tempPlayers = new ArrayList<>();
        for (Player player : getPlayers()) {
            tempPlayers.add(new TempPlayer(player, players.get(player).throwDice()));
            log(player.getName() + " a lancé " + Arrays.toString(players.get(player).getFaceUp()));
        }

        for (TempPlayer tempPlayer : tempPlayers) {
            tempPlayer.setOperations(facetRuleManager.resolve(tempPlayer.getFacets()));
        }
        for (TempPlayer tempPlayer : tempPlayers) {
            tempPlayer.getOperations().forEach(operation -> operation.apply(this, tempPlayer));
        }
    }

    private void action(Player player){
        if(player.chooseAction(this) == 0) {
            Facet facet = player.chooseDiceFacet(this);
            if (facet != null) {
                facet = this.diceSanctuary.buyFacet(facet);
                log(player.getName() + " a acheté " + facet + " pour " + this.diceSanctuary.getPriceForFacet(facet));
                int[] diceChangeFace = player.forgeMyDice(this, facet);
                this.getInventory(player).forge(facet, diceChangeFace[0], diceChangeFace[1]);
            }
        }
        else{
            Card card = player.chooseCard(this);
            if (card != null) {
                card = this.cardSanctuary.buyCard(card);
                //TODO card.executeOperation(player);
                //TODO player.moove(card.getLocationType());
                log(player.getName() + " a effectué le haut fait " + card);
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
