package fr.jugorleans.poker.server.core.test;

import com.google.common.collect.Sets;
import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.play.Deck;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.play.Deck}
 */
public class DeckTest {

    Deck deck = new Deck();

    @Test
    public void initTest() {
        Assert.assertEquals(52, deck.cardsLeft());
    }

    @Test
    public void checkOnlyUniqueCardInDeskTest() {
        Set<String> cards = Sets.newHashSet();
        for (int i = 0; i < 52; i++) {
            cards.add(deck.deal().getValue());
        }
        Assert.assertEquals(0, deck.cardsLeft());
        deck.shuffleUp();
        Assert.assertEquals(52, deck.cardsLeft());
    }

    @Test(expected = IllegalStateException.class)
    public void dealToManyCardTest() {
        for (int i = 0; i < 53; i++) {
            deck.deal();
        }
    }

    @Test
    public void dealFlopTest() {
        deck.shuffleUp();
        List<Card> flop = deck.deal(3);
        Assert.assertEquals(3, flop.size());
    }
}
