package com.github.l3pi;

import com.github.l3pi.bot.IntelligentBot;
import com.github.l3pi.bot.RandomBot;
import com.github.l3pi.game.Factory;
import com.github.l3pi.game.Game;
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

        List<Player> players = createPlayers(playerCount);

        VictoryCounter counter = initializeCounter(players);

        log(Log.State.SYS, String.format("Le programme va lancer %d parties à %d joueurs", runCount, players.size()));

        int currentRun = 1;
        do {
            log(Log.State.SYS, String.format("Partie %d/%d", currentRun, runCount));
            List<Player> winners = run(players);
            counter.addVictoryFor(winners);
        } while (++currentRun <= runCount);

        log(Log.State.SYS, counter.toString());
    }


    private static List<Player> createPlayers(int playerCount) {
        List<Player> players = new ArrayList<>();
        switch (playerCount) {
            case 4:
                players.add(0, new IntelligentBot(4));
            case 3:
                players.add(0, new IntelligentBot(3));
            default:
                players.add(0, new RandomBot(2));
                players.add(0, new IntelligentBot(1));
        }

        return players;
    }

    private static VictoryCounter initializeCounter(List<Player> players) {
        return new VictoryCounter(players);
    }

    private static List<Player> run(List<Player> players) {
        Game game = new Game(Factory.generateInventories(players), Factory.generateDiceSanctuary(players), Factory.generateCardSanctuary(players), 1, players.size() == 3 ? 10 : 9);
        GameManager gameManager = new GameManager(game);
        List<Player> winners = gameManager.run();

        log(Log.State.SYS,
            (winners.size() == 1 ? "Le gagnant est: " : "Les gagnants sont ") +
                winners.stream().map(Player::getName).collect(Collectors.joining(", ")) +
                "\n");

        return winners;
    }
}
