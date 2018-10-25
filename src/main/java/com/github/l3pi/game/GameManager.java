package com.github.l3pi.game;

import com.github.l3pi.type.ResourceType;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GameManager {
    private Game game;

    public GameManager(List<Player> players){
        this.game = new Game(players, RuleFactory.getInstance().getRules(players.size()));
    }

    public List<Player> run(){
        for(int round = 0; round < 9; round++ ) {
            System.out.println(String.format("==========Round %d==========\n",round));
            game.round();
            System.out.println(String.format(game.toString()));
            System.out.println(String.format("\n========End Round %d========\n",round));
        }

        List<Player> players = game.getPlayers();
        int max = players.stream().max(Comparator.comparingInt(player -> player.resources.get(ResourceType.GLORY))).get().resources.get(ResourceType.GLORY);
        return players.stream().filter(player -> player.resources.get(ResourceType.GLORY) == max).collect(Collectors.toList());
    }
}
