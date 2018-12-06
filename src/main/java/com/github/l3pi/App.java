package com.github.l3pi;

import com.github.l3pi.bot.RandomBot;
import com.github.l3pi.game.GameManager;
import com.github.l3pi.game.Player;
import com.github.l3pi.sys.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.Log.log;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: <run-count> <player-count(>=2)>");
            System.exit(1);
        }

        // TODO will be used later when introducing multiple games.
        int runCount = Integer.parseInt(args[0]),
            playerCount = Integer.parseInt(args[1]);

        runCount = runCount >= 1 ? runCount : 1;

        if (runCount == 1) {
            // Single run
            Log.enableLog();
        }

        log(Log.State.SYS, String.format("Le programme va lancer %d parties", runCount));

        int currentRun = 1;
        do {
            log(Log.State.SYS, String.format("Partie %d/%d", currentRun, runCount));

            run();
        } while(currentRun++ <= runCount);
    }

    private static void run() {
        List<Player> players = new ArrayList<>();
        players.add(new RandomBot("player1"));
        players.add(new RandomBot("player2"));
        GameManager gameManager = new GameManager(players);
        List<Player> winners = gameManager.run();

        String b = (winners.size() == 1 ? "the winner is : " : "the winners are : ") +
            winners.stream().map(Player::getName).collect(Collectors.joining(", ")) +
            "\n";
        log(Log.State.SYS, b);
    }
}
