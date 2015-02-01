package fr.jugorleans.poker.server.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * Représente un paquet de carte
 */
public class Deck {

    /**
     * Le paquet de carte
     */
    private List<Card> deck = Lists.newArrayList();

    /**
     * Le nombre de carte sortie du paquet
     */
    private int cardsUsed = 0;

    /**
     * Constructeur par défaut.
     * <p/>
     * Ajoute les 52 cartes dans le paquet
     */
    public Deck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                deck.add(Card.newBuilder().value(value).suit(suit).build());
            }
        }
    }

    /**
     * Mélange les cartes
     */
    public void shuffleUp() {
        Collections.shuffle(this.deck);
        cardsUsed = 0;
    }

    /**
     * Distribuer une carte
     *
     * @return la carte retirée du parquet
     */
    public Card deal() {
        Preconditions.checkState(this.deck.size() > cardsUsed);
        Card cardDealed = deck.get(cardsUsed);
        cardsUsed++;
        return cardDealed;
    }

    /**
     * Distribuer un nombre de carte donéné
     *
     * @param nb le nombre de carte souhaité
     * @return les cartes
     */
    public List<Card> deal(int nb) {
        List<Card> cards = Lists.newArrayList();
        for (int i = 0; i < nb; i++) {
            cards.add(deal());
        }
        return cards;
    }

    /**
     * @return Le nombre de carte restant dans le paquet
     */
    public int cardsLeft() {
        return deck.size() - cardsUsed;
    }
}
