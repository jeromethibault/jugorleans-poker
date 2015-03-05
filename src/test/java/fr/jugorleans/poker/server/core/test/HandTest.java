package fr.jugorleans.poker.server.core.test;

import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardSuit;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.hand.Hand;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.hand.Hand}
 */
public class HandTest {

    @Test
    public void handValueTest() {
        Card fistCard = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.DIAMONDS).build();
        Card secondCard = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.SPADES).build();
        Hand hand = Hand.newBuilder().firstCard(fistCard).secondCard(secondCard).build();
        Assert.assertEquals(fistCard.getValue() + secondCard.getValue(), hand.getValue());
    }

    @Test
    public void handSuitedTest() {
        Card fistCard = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.DIAMONDS).build();
        Card secondCard = Card.newBuilder().value(CardValue.KING).suit(CardSuit.DIAMONDS).build();
        Hand hand = Hand.newBuilder().firstCard(fistCard).secondCard(secondCard).build();
        Assert.assertTrue(hand.isSuited());
        Assert.assertFalse(hand.isPocket());
    }

    @Test
    public void pocketHandTest() {
        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.DIAMONDS)
                .secondCard(CardValue.ACE, CardSuit.CLUBS).build();
        Assert.assertTrue(hand.isPocket());
        Assert.assertFalse(hand.isSuited());
    }

    @Test(expected = IllegalArgumentException.class)
    public void firstCardNullTest() {
        Hand.newBuilder().firstCard(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void secondCardNullTest() {
        Hand.newBuilder().firstCard(null);
    }

    @Test(expected = IllegalStateException.class)
    public void handWithSameCardTest() {
        Hand hand = Hand.newBuilder().firstCard(CardValue.ACE, CardSuit.DIAMONDS)
                .secondCard(CardValue.ACE, CardSuit.DIAMONDS).build();
    }


}
