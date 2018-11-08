package com.github.l3pi.game;

import com.github.l3pi.factory.InventoryFactory;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private HashMap<Player, Inventory> players;
    private FacetRuleManager facetRuleManager;

    public Game(List<Player> players, RuleSet ruleSet) {
        this.players = new HashMap<>();

        for (int i = 0; i < players.size(); i++) {
            this.players.put(players.get(i), InventoryFactory.getInstance().getInventory(i));
        }

        this.facetRuleManager = new FacetRuleManager(ruleSet);
    }

    public void round() {
        List<TempPlayer> tempPlayers = new ArrayList<>();
        for (Player player : getPlayers()) {
            tempPlayers.add(new TempPlayer(player, players.get(player).throwDice()));
        }
        for (TempPlayer tempPlayer : tempPlayers){
            tempPlayer.setOperations(facetRuleManager.resolve(tempPlayer.getFacets()));
        }
        for (TempPlayer tempPlayer : tempPlayers){
            tempPlayer.getOperations().forEach(operation -> operation.apply(this, tempPlayer));
        }
    }

    private Set<Player> getPlayers() {
        return players.keySet();
    }

    public Inventory getInventory(Player player) {
        return players.get(player);
    }

    public List<Player> getBestPlayer() {
        int max = players.values()
            .stream()
            .max(Comparator.comparingInt(inventory -> inventory.getRessouce(ResourceType.GLORY)))
            .get()
            .getRessouce(ResourceType.GLORY);
        return players
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().getRessouce(ResourceType.GLORY) == max)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    //TODO P2 : GAME STATE HISTORY

    public String toString(Player player){
        return player.toString() + "\n" + players.get(player).toString();
    }

    @Override
    public String toString() {
        return getPlayers().stream().map(this::toString).collect(Collectors.joining("\n\n"));
    }
}
