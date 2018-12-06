package com.github.l3pi.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void addVictoryFor(String player) {
        scores.replace(player, scores.get(player) + 1);
    }


    @Override
    public String toString() {
        return String.format("%d parties ont été lancées, le nombre de victoire par joueur sont:\n", playCounter)
            + scores.entrySet().stream()
                .map(e -> String.format("\t- %s a gagné %d fois", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));
    }
}
