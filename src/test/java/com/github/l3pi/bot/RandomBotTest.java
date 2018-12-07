package com.github.l3pi.bot;

import com.github.l3pi.game.*;
import com.github.l3pi.type.ResourceType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RandomBotTest {

    private Player bot;
    private Game game;
    @Before
    public void setUp(){

        this.bot = new RandomBot("playerbot");
        List<Player> p = new ArrayList<Player>();
        p.add(this.bot);
        p.add(new RandomBot("playerbot2"));
        this.game = new Game(p);
    }

    @Test
    public void testMethods(){
        assertTrue(this.bot.chooseFacetToForge(this.game.getDiceSanctuary().getPurchasableInventory(this.game.getInventory(this.bot).getResource(ResourceType.GOLD)),this.game) instanceof Facet);
        assertTrue((this.bot.chooseCard(this.game) instanceof Card)||(this.bot.chooseCard(this.game)==null));

        List<Integer> t = new ArrayList<Integer>(){};
        t.add(0);
        t.add(1);
        t.add(2);
        assertTrue((t.contains(this.bot.chooseAction(this.game))));
        t.add(6);
        ArrayList dice1 = new ArrayList<Facet>() {{
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 SOLAR",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.SOLAR, 1);
                })));
        }};

        ArrayList dice2 = new ArrayList<Facet>() {{
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 GOLD",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GOLD, 1);
                })));
            add(new Facet("1 LUNAR",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.LUNAR, 1);
                })));
            add(new Facet("2 GLORY",
                ((Game game, Player player) -> {
                    game.getInventory(player).addResources(ResourceType.GLORY, 2);
                })));}};
            List<Dice> dices = new ArrayList<Dice>(){{
                add(new Dice(dice1));
                add(new Dice(dice2));
            }};
        assertTrue((t.contains(this.bot.chooseDice(dices))));
    }

}
