package com.github.l3pi.game;

import com.github.l3pi.factory.RuleFactory;

import java.util.List;
import java.util.Set;

public class GameManager {
    private List<Player> players;

    public GameManager(List<Player> players){
        this.players = players;
    }

    public List<Player> run() {
        Game game = new Game(players, RuleFactory.getInstance().getRules(players.size()));
        for (int round = 1; round <= 9; round++) {
            System.out.println(String.format("==========Round %d==========\n",round));
            game.round();
            System.out.println(game.toString());
            System.out.println(String.format("\n========End Round %d========\n",round));
        }
        return game.getBestPlayer();
    }

}
