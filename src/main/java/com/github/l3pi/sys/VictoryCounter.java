package com.github.l3pi.sys;

import java.util.*;

public class VictoryCounter {
    private int playCounter;
    private final Map<String, Integer> scores = new HashMap<>();

    public VictoryCounter(List<String> playerNames) {
        for (String name : playerNames) {
            scores.put(name, 0);
        }
    }

    public void addVictoryFor(List<String> players) {
        playCounter++;

        for (String name : players) {
            addVictoryFor(name);
        }
    }

    private void addVictoryFor(String player) {
        if (scores.containsKey(player)) {
            scores.replace(player, scores.get(player) + 1);
        }
    }

    int getVictoryCountFor(String player) {
        return scores.getOrDefault(player, 0);
    }

    int getPlayCount() {
        return playCounter;
    }

    private double getTotalWinCount() {
        return scores.values().stream().mapToInt(i -> i).sum();
    }

    private double getWinPercentageFor(String player) {
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
