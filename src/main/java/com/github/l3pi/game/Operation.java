package com.github.l3pi.game;

/**
 * An operation is a simple action made to modify the game state.
 *
 * @see com.github.l3pi.rule.FacetRule
 * <p>
 * c'est l'interface fonctionnelle pour les effects des cartes et les effects des face de dé
 */
public interface Operation {
    void apply(Game game, Player player);
}
