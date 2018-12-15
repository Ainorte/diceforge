package com.github.l3pi.game;

import com.github.l3pi.sys.Log;
import com.github.l3pi.type.CardLocationType;
import com.github.l3pi.type.ResourceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardSanctuary {

    /**
     * c'est la classe qui représente un sanctuaire des cartes
     *
     * @param cardSanctuary c'est le hashmap qui associe une carte a son nombre de carte sur un emplacement du sanctuaire
     * <p>
     * chaque object carte est identifié par son hash et initialisé au debut tous a 4 nombre d'examplaire
     */
    private HashMap<Card, Integer> cardSanctuary;
    private HashMap<Player, CardLocationType> locations;

    public CardSanctuary(HashMap<Card, Integer> cardSanctuary, HashMap<Player, CardLocationType> locations) {
        this.cardSanctuary = new HashMap<>(cardSanctuary);
        this.locations = new HashMap<>(locations);
    }

    /**
     * return une liste de carte qui sont encore en stock dans le sanctuaire
     */

    public List<Card> getAvailableCard() {
        return cardSanctuary.entrySet().stream().filter(entry -> entry.getValue() > 0)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * return une liste de carte qui sont achetable depuis un inventaire d'un autre joueur
     *
     * @param resources ressouces d'un joueur .
     */

    public List<Card> getPurchasableCard(HashMap<ResourceType, Integer> resources) {
        return cardSanctuary.entrySet()
            .stream()
            .filter(entry -> entry.getValue() > 0 && entry.getKey().isPurchasable(resources))
            .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * retourne null si on ne peux plus acheter ou on a pas donné de carte a acheté
     * et met a jour le stock des cartes
     *
     * @param card c'est la carte séléctionné pour achat dans le sanctuaire
     */


    Card buyCard(Card card) {
        if (cardSanctuary.getOrDefault(card, 0) > 0) {
            cardSanctuary.put(card, cardSanctuary.get(card) - 1);
            return card;
        }
        return null;
    }

    public CardLocationType getPlayerLocation(Player player) {
        return locations.get(player);
    }

    Player getPlayerOnLocation(CardLocationType location) {
        if (locations.containsValue(location)) {
            return locations
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(location))
                .collect(Collectors.toList())
                .get(0)
                .getKey();
        }
        return null;
    }

    Player move(CardLocationType location, Player player) {
        Player old = getPlayerOnLocation(location);
        if (old == null || location.equals(CardLocationType.CENTER)) {
            locations.replace(player, location);
        } else if (!old.equals(player)) {
            locations.replace(old, CardLocationType.CENTER);
            locations.replace(player, location);
            return old;
        }
        return null;
    }

    @Override
    public String toString() {
        return Log.State.fmt(Log.State.GREEN, cardSanctuary.entrySet().stream()
            .map(facet -> String.format("\tNombre de cartes de type \"%s\" disponible: %s", facet.getKey(), facet.getValue()))
            .collect(Collectors.joining("\n")));
    }
}
