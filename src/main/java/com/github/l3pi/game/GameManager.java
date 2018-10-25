package com.github.l3pi.game;

public class GameManager {
    private Game game;

    public GameManager(Game game){
        this.game = game;
    }

    public void run(){
        for(int round = 0; round < 9; round++ ) {
            game.round();
            System.out.println(String.format("Round: %d\n%s",round,game));
        }
    }
}
