package com.github.l3pi.game;

import java.util.List;

import static com.github.l3pi.sys.Log.log;

/**
 * Class tasked with handling a game lifecycle, including rounds.
 */
public class GameManager {
    /** Cette classe prend une liste de player et l'instancie a game , et fait tourner la partie
     *
     * @param players la liste des joueurs pour cette partie ,
     *                Voir App qui execute le jeu en entier
     *
     * */
    private List<Player> players;

    public GameManager(List<Player> players) {
        this.players = players;
    }

    /** Tourne une partie de jeu et renvoie le gagnant
     *
     * */
    public List<Player> run() {
        Game game = new Game(players);
        log(game.toString());
        for (int round = 1; round <= 9; round++) {
            log(String.format("==========Round %d==========\n", round));
            game.round();
            game.increaseRound();
            log(game.toString());
            log(String.format("\n========End Round %d========\n", round));
        }
        return game.getBestPlayer();
    }

}
