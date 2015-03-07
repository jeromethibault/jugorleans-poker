package fr.jugorleans.poker.server.core.test;

import fr.jugorleans.poker.server.core.hand.Card;
import fr.jugorleans.poker.server.core.hand.CardSuit;
import fr.jugorleans.poker.server.core.hand.CardValue;
import fr.jugorleans.poker.server.core.play.Board;
import fr.jugorleans.poker.server.core.play.Deck;
import org.junit.Assert;
import org.junit.Test;

/**
 * Classe de test de {@link fr.jugorleans.poker.server.core.play.Board}
 */
public class BoardTest {

    private Board board = new Board();

    @Test(expected = IllegalArgumentException.class)
    public void addNullCardTest() {
        board.addCard(null);
    }
    @Test(expected = IllegalArgumentException.class)
     public void addNullCardsTest() {
        board.addCards(null);
    }

    @Test(expected = IllegalStateException.class)
    public void addSixCardTest() {
        Deck deck = new Deck();
        for (int i = 0; i < 6; i++) {
            board.addCard(deck.deal());
        }
    }

    @Test
    public void boardTest() {
        board.clear();
        Deck deck = new Deck();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            Card card = deck.deal();
            builder.append(card.getValue());
            board.addCard(card);
        }
        Assert.assertEquals(5, board.nbCard());
        Assert.assertEquals(builder.toString(), board.toString());
        board.clear();
        Assert.assertEquals(0, board.nbCard());
    }

    @Test
    public void nbTest() {
        board.clear();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card2);

        Assert.assertEquals(3, board.nbCard());
        Assert.assertEquals(2, board.nb(CardValue.EIGHT));
        Assert.assertEquals(1, board.nb(CardValue.FIVE));
    }

    @Test
    public void nbUniqueValueCardTest() {
        board.clear();
        Card card = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.SEVEN).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);
        Assert.assertEquals(4, board.nbUniqueValueCard());
    }

    @Test
    public void nbUniqueCardTest2() {
        board.clear();
        Card card = Card.newBuilder().value(CardValue.ACE).suit(CardSuit.CLUBS).build();
        board.addCard(card);
        Card card1 = Card.newBuilder().value(CardValue.FIVE).suit(CardSuit.CLUBS).build();
        board.addCard(card1);
        Card card2 = Card.newBuilder().value(CardValue.EIGHT).suit(CardSuit.HEARTS).build();
        board.addCard(card2);
        Card card3 = Card.newBuilder().value(CardValue.SEVEN).suit(CardSuit.HEARTS).build();
        board.addCard(card3);
        Card card4 = Card.newBuilder().value(CardValue.TWO).suit(CardSuit.HEARTS).build();
        board.addCard(card4);
        Assert.assertEquals(5, board.nbUniqueValueCard());
    }


}
