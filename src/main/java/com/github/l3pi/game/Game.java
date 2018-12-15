package com.github.l3pi.game;

import com.github.l3pi.sys.Log;
import com.github.l3pi.type.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.l3pi.sys.Log.log;

/**
 * Game instance
 */
public class Game {
    /**
     * la classe Game gere tout ce qui est interaction du jeu pour chaque tour, pour faire un jeu complet voir GameManager
     * qui executer le nombre de tours correspondant aux nombres de joueurs
     * <p>
     * la variable players est une map ordonnée de la classe player qui associe son inventaire
     * la variable diceSanctuary est la variable qui représente le sanctuaire des dés
     * la variable cardSanctuary est la variable qui représente le sanctuaire des cartes
     */

    private TreeMap<Player, Inventory> players;
    private DiceSanctuary diceSanctuary;
    private CardSanctuary cardSanctuary;
    private int round;
    private int maxRound;

    /**
     * On instancie Game avec une liste de joueurs
     *
     * @param players a liste de joueurs pour cette partie
     */
    public Game(TreeMap<Player, Inventory> players, DiceSanctuary diceSanctuary, CardSanctuary cardSanctuary, int round, int maxRound) {
        this.players = new TreeMap<>(players);
        this.diceSanctuary = diceSanctuary;
        this.cardSanctuary = cardSanctuary;
        this.round = round;
        this.maxRound = maxRound;
    }

    public int getRound() {
        return this.round;
    }

    public boolean isFinish() {
        return round >= maxRound;
    }

    /**
     * applique un tour de jeu
     */
    void round() {
        if (!isFinish()) {
            round++;
            log(Log.State.RED, String.format("\n======================================== Tour %d ========================================\n", round));
            for (Player player : this.getPlayers()) {
                round(player);
            }
            log(Log.State.GREEN, "Etat du jeu à la fin du tour " + getRound());
            log();
            log(Log.State.GREEN, toString());
            log(Log.State.RED, String.format("\n====================================== Fin Tour %d ======================================\n", round));
        }
    }

    /**
     * Applique un tour de jeu et gere l'ordre des actions pendant un tour
     */
    private void round(Player player) {
        log(Log.State.GREEN, "Tour de jeu de " + player);
        log();
        if (getPlayers().size() == 2) {
            divineBlessing();
        }
        divineBlessing();
        log();
        recurrentAction(player);
        log();
        action(player);
        log();
        log();
    }

    /**
     * l'action qui permet de lancer le dé des joueurs l'un aprés l'autre
     */
    private void divineBlessing() {
        log(Log.State.RED, "Faveur divine lancée pour tous les joueurs");
        for (Player player : getPlayers()) {
            List<Facet> facetUp = getInventory(player).throwDices();
            log(Log.State.GREEN, "\t" + player + Log.State.GREEN + " a lancé " + facetUp);

        }
        for (Player player : getPlayers()) {
            getInventory(player)
                .getFaceUp()
                .forEach(facet -> facet.getOperation().apply(this, player));
        }
    }

    private void recurrentAction(Player player) {
        log(Log.State.RED, "Application des cartes récurrentes de " + player);
        getInventory(player)
            .getCardInventory().stream()
            .filter(Card::isRecurrent)
            .forEach(card -> card.getOperation().apply(this, player));
    }

    /**
     * cette fonction applique une action en fonction du choix du joueur
     * par exemple le joueur peut decider d'acheter une carte ou de forger une face de dé
     *
     * @param player le player qui effectue l'action
     */
    private void action(Player player) {
        log(Log.State.RED, player + Log.State.RED + " joue");
        Inventory inventory = this.players.get(player);
        action(player.chooseAction(this), player);
        if (inventory.getResource(ResourceType.SOLAR) >= 2 && player.moreAction(this)) {
            inventory.addResources(ResourceType.SOLAR, -2);
            action(player.chooseAction(this), player);
        }
    }

    private void action(int action, Player player) {
        Inventory inventory = this.players.get(player);
        if (action == 0) {
            Facet facet;
            do {
                facet = player.chooseFacetToForge(this.getDiceSanctuary().getPurchasableFacets(this.getInventory(player).getResource(ResourceType.GOLD)), this);
                facet = this.diceSanctuary.buyFacet(facet);
                if (facet != null) {
                    inventory.addResources(ResourceType.GOLD, -facet.getGoldCost());
                    log(Log.State.GREEN, "\t" + player + Log.State.GREEN + " a acheté la face de dés " + facet + " pour " + facet.getGoldCost() + " gold");
                    int[] diceChangeFace = player.forgeDice(this, facet);
                    this.getInventory(player).forge(facet, diceChangeFace[0], diceChangeFace[1]);
                }
            } while (facet != null);
        } else if (action == 1) {
            Card card = player.chooseCard(this);
            card = this.cardSanctuary.buyCard(card);
            if (card != null) {
                final int cost = card.getPrice();
                card.getResourceType().forEach(resource -> {
                    inventory.addResources(resource, -cost);
                });
                Player old = cardSanctuary.move(card.getLocation(), player);

                inventory.addCard(card);

                log(Log.State.GREEN, "\t" + player + Log.State.GREEN + " a acheté la carte " + card + " et se situe sur la case " + card.getLocation() + " du plateau");

                if (!card.isRecurrent()) {
                    card.getOperation().apply(this, player);
                }

                if (old != null) {
                    log(Log.State.GREEN, "\t" + player + Log.State.GREEN + " a chasser " + old);
                    log(Log.State.RED, "\t" + "Faveur divine lancée pour " + old);
                    List<Facet> facetUp = getInventory(old).throwDices();
                    log(Log.State.GREEN, "\t" + old + Log.State.GREEN + " a lancé " + facetUp);
                    facetUp.forEach(facet -> facet.getOperation().apply(this, old));
                }
            }
        }
    }


    public DiceSanctuary getDiceSanctuary() {
        return diceSanctuary;
    }

    public CardSanctuary getCardSanctuary() {
        return cardSanctuary;
    }

    List<Player> getPlayers() {
        return new ArrayList<>(players.keySet());
    }

    Player getPlayer(int number) {
        return players.keySet().stream()
            .filter(t -> t.getId() == number)
            .findFirst().orElse(null);
    }

    /**
     * prend un player en parametre et retourne l'inventaire associé au joueur
     */
    public Inventory getInventory(Player player) {
        return players.get(player);
    }

    public HashMap<Integer, Inventory> getInventories() {
        return new HashMap<>(players.entrySet().stream().collect(Collectors.toMap((Map.Entry<Player, Inventory> e) -> e.getKey().getId(), Map.Entry::getValue)));
    }

    /**
     * cette fonction renvoie les gagnants
     *
     * @return une liste contenant les gagnants
     */
    List<Player> getBestPlayer() {
        if (players.size() == 0) {
            return new ArrayList<>();
        }
        int max = players.values()
            .stream()
            .max(Comparator.comparingInt(inventory -> inventory.getResource(ResourceType.GLORY)))
            .get()
            .getResource(ResourceType.GLORY);
        return players
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().getResource(ResourceType.GLORY) == max)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return getPlayers().stream().map(player -> player.toFmtString() + "\n" + Log.State.fmt(Log.State.GREEN, getInventory(player) + "\n\tLocalisation\t:\t" + cardSanctuary.getPlayerLocation(player))).collect(Collectors.joining("\n"))
            + "\n" + Log.State.fmt(Log.State.BLUE, "État du sanctuaire de dés") + "\n" + diceSanctuary
            + "\n" + Log.State.fmt(Log.State.BLUE, "État du sanctuaire de cartes") + "\n" + cardSanctuary;
    }
}
