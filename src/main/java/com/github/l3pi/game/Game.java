package com.github.l3pi.game;

import com.github.l3pi.rule.RuleSet;

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

    public void run(){
        //TODO : Foreach
        //TODO : Throw dices
        //TODO : Get operation from manager
        //TODO : Apply operation on game states

        //TODO P2 : GAME STATE HISTORY
    }
}
