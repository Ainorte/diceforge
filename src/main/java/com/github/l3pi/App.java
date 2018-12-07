package com.github.l3pi;

import com.github.l3pi.bot.IntelligentBot;
import com.github.l3pi.bot.RandomBot;
import com.github.l3pi.game.GameManager;
import com.github.l3pi.game.Player;
import com.github.l3pi.sys.Log;
import com.github.l3pi.sys.VictoryCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.Log.log;

/**
 * Hello world!
 */
public class App {
    private static VictoryCounter counter;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: <run-count> <player-count(>=2)>");
            System.exit(1);
        }

        Log.describeColourCodes();

        int runCount = Integer.parseInt(args[0]),
            playerCount = Integer.parseInt(args[1]);

        runCount = runCount >= 1 ? runCount : 1;

        if (runCount == 1) {
            // Single run
            Log.enableLog();
        }

        initializeCounter(playerCount);

        log(Log.State.SYS, String.format("Le programme va lancer %d parties à %d joueurs", runCount, playerCount));

        int currentRun = 1;
        do {
            log(Log.State.SYS, String.format("Partie %d/%d", currentRun, runCount));

            run(playerCount);
        } while (++currentRun <= runCount);

        log(Log.State.SYS, counter.toString());
    }

    private static void initializeCounter(int playerCount) {
        List<String> names = new ArrayList<>();

        switch (playerCount) {
            case 4:
                names.add("player3");
                names.add("player4");
            case 2:
            default:
                names.add(0, "player1");
                names.add(1, "player2");
        }

        counter = new VictoryCounter(names);
    }

    private static void run(int playerCount) {
        List<Player> players = new ArrayList<>();

        switch (playerCount) {
            case 4:
                players.add(new RandomBot("player3"));
                players.add(new RandomBot("player4"));
            case 2:
            default:
                players.add(0, new IntelligentBot("player1")); //MODIFIER ICI
                players.add(1, new RandomBot("player2"));
        }
        GameManager gameManager = new GameManager(players);
        List<Player> winners = gameManager.run();

        counter.addVictoryFor(winners.stream().map(Player::getName).collect(Collectors.toList()));

        String b = (winners.size() == 1 ? "Le gagnant est: " : "Les gagnants sont ") +
            winners.stream().map(Player::getName).collect(Collectors.joining(", ")) +
            "\n";
        log(Log.State.SYS, b);
    }
}
