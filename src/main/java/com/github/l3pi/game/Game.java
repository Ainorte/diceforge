package com.github.l3pi.game;

import com.github.l3pi.rule.RuleSet;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private RuleSet ruleSet;
    private FacetRuleManager facetRuleManager;

    public Game(List<Player> players, RuleSet ruleSet, FacetRuleManager facetRuleManager) {
        this.players = players;
        this.ruleSet = ruleSet;
        this.facetRuleManager = facetRuleManager;
    }

    public void round() {
        List<TempPlayer> tempPlayers = new ArrayList<TempPlayer>();
        for (Player player : players) {
            tempPlayers.add(new TempPlayer(player, player.throwDice()));
        }
        //TODO : Get operation from manager
        //TODO : Apply operation on game states
    }

    //TODO P2 : GAME STATE HISTORY


    @Override
    public String toString() {
        return String.join("/n", (String[]) players.stream().map(player -> player.toString()).toArray());
    }
}
