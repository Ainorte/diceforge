package com.github.l3pi.game;

import java.util.List;

public class GameManager {
    private Game game;

    public GameManager(List<Player> players){
        this.game = new Game(players, RuleFactory.getInstance().getRules(players.size()));
    }

    public void run(){
        for(int round = 0; round < 9; round++ ) {
            game.round();
            System.out.println(String.format("Round: %d\n%s",round,game));
        }
    }
}
