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
    private HashMap<Player, Inventory> players;
    private FacetRuleManager facetRuleManager;
    private DiceSanctuary diceSanctuary;

    public Game(List<Player> players, RuleSet ruleSet) {
        this.players = new HashMap<>();
        this.diceSanctuary = new DiceSanctuary();

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
        Facet facet = player.chooseDiceFacet(this);
        this.diceSanctuary.buyFacet(this.getInventory(player), facet);
        if (facet != null) {
            log(player.getName() + " a acheté " + facet + " pour " + this.diceSanctuary.getPriceForFacet(facet));
        }
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


    public DiceSanctuary getDiceSanctuary() {
        return diceSanctuary;
    }

    private Set<Player> getPlayers() {
        return players.keySet();
    }

    public Inventory getInventory(Player player) {
        return players.get(player);
    }

    List<Player> getBestPlayer() {
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
