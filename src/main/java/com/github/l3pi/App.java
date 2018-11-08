package com.github.l3pi;

import com.github.l3pi.bot.RandomBot;
import com.github.l3pi.game.GameManager;
import com.github.l3pi.game.Player;
import com.github.l3pi.sys.LogDAO;
import com.github.l3pi.sys.log.Logger;
import com.github.l3pi.sys.log.StringLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.LogDAO.log;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("Usage: %s <run-count> <player-count(>=2)>", args[0]);
            System.exit(1);
        }

        LogDAO.initialize(new ArrayList<Logger>() {{
            add(new StringLogger());
        }}, LogDAO.LogOutputPolicy.ON_LOG);

        List<Player> players = new ArrayList<Player>();
        players.add(new RandomBot("player1"));
        players.add(new RandomBot("player2"));
        GameManager gameManager = new GameManager(players);
        List<Player> winners = gameManager.run();

        String b = "the winners are : " +
            winners.stream().map(Player::getName).collect(Collectors.joining(", ")) +
            "\n";
        log(b);
    }
}
