package com.github.l3pi.sys;

import com.github.l3pi.game.Player;

import java.util.List;
import java.util.TreeMap;

public class VictoryCounter {
    private final TreeMap<Player, Integer> scores = new TreeMap<>();
    private int playCounter;

    public VictoryCounter(List<Player> players) {
        for (Player player : players) {
            scores.put(player, 0);
        }
    }

    public void addVictoryFor(List<Player> players) {
        playCounter++;

        for (Player player : players) {
            addVictoryFor(player);
        }
    }

    private void addVictoryFor(Player player) {
        if (scores.containsKey(player)) {
            scores.merge(player, 1, Math::addExact);
        }
    }

    int getVictoryCountFor(Player player) {
        return scores.getOrDefault(player, 0);
    }

    int getPlayCount() {
        return playCounter;
    }

    private double getTotalWinCount() {
        return scores.values().stream().mapToInt(i -> i).sum();
    }

    private double getWinPercentageFor(Player player) {
        double percentage = 0.;

        if (scores.containsKey(player)) {
            percentage = (scores.get(player) / getTotalWinCount()) * 100.;
        }

        return percentage;
    }

    private String getStats() {
        final StringBuilder b = new StringBuilder();

        final int totalWinCount = (int) getTotalWinCount();
        b.append(String.format("Il y a eu %d parties\n", playCounter));
        b.append(String.format("Il y a eu un total cumulé de %d victoire%s\n", totalWinCount, totalWinCount != 1 ? "s" : ""));
        b.append(Log.State.fmt(Log.State.BLUE, "Nombre de victoires\n"));
        scores.forEach(
            (player, score) ->
                b
                    .append(Log.State.GREEN)
                    .append("\t- ")
                    .append(player)
                    .append(Log.State.GREEN)
                    .append(": ")
                    .append(score)
                    .append(" (")
                    .append(String.format("%3.2f", getWinPercentageFor(player)))
                    .append("%)\n")
        );

        b.append(Log.State.RESET);
        return b.toString();
    }

    @Override
    public String toString() {
        return getStats();
    }
}
