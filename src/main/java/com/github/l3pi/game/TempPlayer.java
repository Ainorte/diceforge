package com.github.l3pi.game;

import java.util.List;

public final class TempPlayer {
    private final Player player;
    private final List<Facet> facets;
    private List<Operation> operations;

    public Player getPlayer() {
        return player;
    }

    public List<Facet> getFacets() {
        return facets;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public TempPlayer(Player player, List<Facet> facets) {
        this.player = player;
        this.facets = facets;
    }
}
