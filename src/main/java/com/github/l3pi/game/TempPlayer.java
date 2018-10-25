package com.github.l3pi.game;

import java.util.List;

public final class TempPlayer {
    public final Player player;
    public final List<Facet> facets;
    public List<Operation> operations;

    public TempPlayer(Player player, List<Facet> facets) {
        this.player = player;
        this.facets = facets;
    }
}
