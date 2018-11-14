package com.github.l3pi.game;

import com.github.l3pi.factory.InventoryFactory;
import com.github.l3pi.rule.RuleSet;
import com.github.l3pi.type.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.LogDAO.log;

public class Game {
    private HashMap<Player, Inventory> players;
    private FacetRuleManager facetRuleManager;
    private SanctuaryDice sanctuaryDice;

    public Game(List<Player> players, RuleSet ruleSet) {
        this.players = new HashMap<>();
        this.sanctuaryDice = new SanctuaryDice();

        for (int i = 0; i < players.size(); i++) {
            this.players.put(players.get(i), InventoryFactory.getInstance().getInventory(this,players.get(i),i));
        }

        this.facetRuleManager = new FacetRuleManager(ruleSet);
    }

    public void round(){
        for(Player player:this.getPlayers()){
            round(player);
        }

    }


    private void round(Player player) {

        divineBlessing();
        Facet facet = player.chooseDiceFacet(this);
        this.sanctuaryDice.buyFacet(this.getInventory(player),facet);
        if(facet != null){
            log(player.getName() + " à acheté " + facet + " pour "+ this.sanctuaryDice.getDiceSanctuary().get(facet).getPrice());

        }
    }

    private void divineBlessing(){

        List<TempPlayer> tempPlayers = new ArrayList<>();
        for (Player player : getPlayers()) {
            tempPlayers.add(new TempPlayer(player, players.get(player).throwDice()));
            log(player.getName() + " à lancé " + Arrays.toString(players.get(player).getFaceUp()));
        }

        for (TempPlayer tempPlayer : tempPlayers){
            tempPlayer.setOperations(facetRuleManager.resolve(tempPlayer.getFacets()));
        }
        for (TempPlayer tempPlayer : tempPlayers){
            tempPlayer.getOperations().forEach(operation -> operation.apply(this, tempPlayer));
        }
    }


    public SanctuaryDice getSanctuaryDice() {
        return sanctuaryDice;
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

    public String toString(Player player){
        return player.toString() + "\n" + players.get(player).toString();
    }

    @Override
    public String toString() {
        return getPlayers().stream().map(this::toString).collect(Collectors.joining("\n\n"));
    }
}
