package com.github.l3pi.game;

import java.util.List;

import static com.github.l3pi.sys.Log.log;

/**
 * Class tasked with handling a game lifecycle, including rounds.
 */
public class GameManager {
    /**
     * Cette classe permet de protéger l'execution de run de la classe Game
     * @see Game
     * @see com.github.l3pi.App
     *
     * @param game la partie à lancer ,
     *
     */
    private Game game;

    public GameManager(Game game) {
        this.game = game;
    }

    /**
     * Tourne la partie de jeu
     */
    public List<Player> run() {
        log(game.toString());
        while (!game.isFinish()) {
            game.round();
        }
        return game.getBestPlayer();
    }
}
