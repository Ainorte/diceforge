package com.github.l3pi.game;

import com.github.l3pi.rule.RuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players;
    private FacetRuleManager facetRuleManager;

    public Game(List<Player> players, RuleSet ruleSet) {
        this.players = players;
        this.facetRuleManager = new FacetRuleManager(ruleSet);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void round() {
        List<TempPlayer> tempPlayers = new ArrayList<>();
        for (Player player : players) {
            tempPlayers.add(new TempPlayer(player, player.throwDice()));
        }
        for (TempPlayer tempPlayer : tempPlayers){
            tempPlayer.operations = facetRuleManager.resolve(tempPlayer.facets);
        }
        for (TempPlayer tempPlayer : tempPlayers){
            tempPlayer.operations.forEach(operation -> operation.apply(this,tempPlayer));
        }
    }

    //TODO P2 : GAME STATE HISTORY

    @Override
    public String toString() {
        return players.stream().map(Player::toString).collect(Collectors.joining("\n\n"));
    }
}
