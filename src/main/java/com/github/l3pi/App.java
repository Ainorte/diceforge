package com.github.l3pi;

import com.github.l3pi.game.GameManager;
import com.github.l3pi.game.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        if (args.length != 2) {
            System.err.printf("Usage: %s <run-count> <player-count(>=2)>", args[0]);
        }
        else{
            List<Player> players = new ArrayList<Player>();
            players.add(new Player(3,"player1"));
            players.add(new Player(2,"player2"));
            GameManager gameManager = new GameManager(players);
            gameManager.run();
            List<Player> winners = gameManager.run();
            System.out.print("the winners are : ");
            for(Player p:winners){
                System.out.print(p.getName() +" ");
            }
        }
    }
}
