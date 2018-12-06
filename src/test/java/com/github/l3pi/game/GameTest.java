/*package com.github.l3pi.game;

import com.github.l3pi.bot.RandomBot;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class GameTest {
    private Game game;

    @Before
    public void initGame() {
        game = mock(Game.class);
        game.init(Arrays.asList(
            new RandomBot("1"),
            new RandomBot("2")
        ));
    }

    @Test
    public void testThatMethodsAreAtLeastCalledOnce() {
        game.round();
        verify(game, atLeastOnce()).divineBlessing();
        verify(game).recurrentAction(game.getPlayer(anyString()));
    }
}
*/
